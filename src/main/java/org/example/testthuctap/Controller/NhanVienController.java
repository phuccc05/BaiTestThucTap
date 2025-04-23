package org.example.testthuctap.Controller;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.testthuctap.Model.CoSo;
import org.example.testthuctap.Model.NhanVien;
import org.example.testthuctap.Repository.CoSoRepository;
import org.example.testthuctap.Repository.NhanVienRepository;
import org.example.testthuctap.Service.ChuyenNganhNhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/nhanvien")
public class NhanVienController {

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private CoSoRepository coSoRepository;

    @Autowired
    private ChuyenNganhNhanVienService chuyenNganhNhanVienService;

    @GetMapping("/home")
    public String hienThi(Model model) {
        List<NhanVien> listNV = nhanVienRepository.findAll();
        model.addAttribute("listNV", listNV);
        model.addAttribute("nhanVien", new NhanVien());
        return "/Home.html";
    }

    @PostMapping("/add")
    public String themNhanVien(@ModelAttribute NhanVien nhanVien, Model model) {
        String loi = validateNhanVien(nhanVien, null);
        if (loi != null) {
            return traVeTrangChuVoiLoi(model, loi, nhanVien);
        }

        Date now = new Date();
        nhanVien.setNgayTao(now.getTime());
        nhanVien.setNgaySua(now.getTime());
        nhanVienRepository.save(nhanVien);

        return "redirect:/nhanvien/home";
    }

    @GetMapping("/detail/{id}")
    public String chiTietNhanVien(@PathVariable("id") UUID id, Model model) {
        Optional<NhanVien> nhanVienOpt = nhanVienRepository.findById(id);
        List<NhanVien> listNV = nhanVienRepository.findAll();

        if (nhanVienOpt.isPresent()) {
            model.addAttribute("listNV", listNV);
            model.addAttribute("nhanVien", nhanVienOpt.get());
            model.addAttribute("danhSachCoSo", coSoRepository.findAll());
            return "/Detail.html";
        }

        return "redirect:/nhanvien/home";
    }

    @PostMapping("/update")
    public String updateNhanVien(@ModelAttribute NhanVien nhanVien, Model model) {
        Optional<NhanVien> oldNVOpt = nhanVienRepository.findById(nhanVien.getId());

        if (oldNVOpt.isPresent()) {
            NhanVien oldNV = oldNVOpt.get();
            String loi = validateNhanVien(nhanVien, oldNV);
            if (loi != null) {
                model.addAttribute("nhanVien", nhanVien);
                model.addAttribute("danhSachCoSo", coSoRepository.findAll());
                model.addAttribute("listNV", nhanVienRepository.findAll());
                model.addAttribute("error", loi);
                return "/Detail.html";
            }

            nhanVien.setNgayTao(oldNV.getNgayTao());
            nhanVien.setNgaySua(new Date().getTime());
            nhanVienRepository.save(nhanVien);
        }

        return "redirect:/nhanvien/home";
    }
    @PostMapping("/toggle-status/{id}")
    public String toggleTrangThai(@PathVariable("id") UUID id) {
        Optional<NhanVien> nhanVienOpt = nhanVienRepository.findById(id);
        if (nhanVienOpt.isPresent()) {
            NhanVien nv = nhanVienOpt.get();
            nv.setTrangThai(nv.getTrangThai() == 1 ? 0 : 1); // Đảo trạng thái
            nv.setNgaySua(new Date().getTime());
            nhanVienRepository.save(nv);
        }
        return "redirect:/nhanvien/home";
    }

    private String validateNhanVien(NhanVien nv, NhanVien nvCu) {
        if (nv.getTaiKhoanFE().isBlank() || nv.getTaiKhoanFPT().isBlank() ||
                nv.getTen().isBlank() || nv.getMaNhanVien().isBlank() ||
                nv.getTrangThai() == null) {
            return "Không được bỏ trống các trường.";
        }

        if (nv.getTaiKhoanFE().length() > 100 || nv.getTaiKhoanFPT().length() > 100 ||
                nv.getTen().length() > 100 || nv.getMaNhanVien().length() > 15) {
            return "Tên tài khoản không được quá 100 ký tự, mã không quá 15 ký tự.";
        }

        if (!isValidEmailFormat(nv.getTaiKhoanFPT(), "@fpt.edu.vn") ||
                !isValidEmailFormat(nv.getTaiKhoanFE(), "@fe.edu.vn")) {
            return "Email phải đúng định dạng @fpt.edu.vn hoặc @fe.edu.vn.";
        }

        if (nhanVienRepository.existsByMaNhanVien(nv.getMaNhanVien()) &&
                (nvCu == null || !nvCu.getMaNhanVien().equals(nv.getMaNhanVien()))) {
            return "Mã nhân viên đã tồn tại.";
        }

        if (nhanVienRepository.existsByTaiKhoanFPT(nv.getTaiKhoanFPT()) &&
                (nvCu == null || !nvCu.getTaiKhoanFPT().equals(nv.getTaiKhoanFPT()))) {
            return "Email FPT đã tồn tại.";
        }

        if (nhanVienRepository.existsByTaiKhoanFE(nv.getTaiKhoanFE()) &&
                (nvCu == null || !nvCu.getTaiKhoanFE().equals(nv.getTaiKhoanFE()))) {
            return "Email FE đã tồn tại.";
        }

        return null;
    }

    private boolean isValidEmailFormat(String email, String expectedSuffix) {
        return email.endsWith(expectedSuffix)
                && !email.contains(" ")
                && !containsVietnamese(email);
    }

    private boolean containsVietnamese(String input) {
        String vietnameseRegex = "[àáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễ"
                + "ìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữ"
                + "ỳýỵỷỹđÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẾỆỂỄ"
                + "ÌÍỊỈĨÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮ"
                + "ỲÝỴỶỸĐ]";
        return Pattern.compile(vietnameseRegex).matcher(input).find();
    }

    private String traVeTrangChuVoiLoi(Model model, String errorMsg, NhanVien nhanVien) {
        List<NhanVien> listNV = nhanVienRepository.findAll();
        model.addAttribute("listNV", listNV);
        model.addAttribute("nhanVien", nhanVien);
        model.addAttribute("error", errorMsg);
        return "/Home.html";
    }

    @GetMapping("/download-template/{id}")
    public ResponseEntity<ByteArrayResource> downloadTemplateNhanVien(@PathVariable UUID id) throws IOException {
        Optional<NhanVien> optionalNhanVien = nhanVienRepository.findById(id);
        if (optionalNhanVien.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        NhanVien nv = optionalNhanVien.get();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Employee");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Tài Khoản FE");
        header.createCell(2).setCellValue("Tài Khoản FPT");
        header.createCell(3).setCellValue("Họ Tên");
        header.createCell(4).setCellValue("Mã Nhân Viên");
        header.createCell(5).setCellValue("Trạng Thái");
        header.createCell(6).setCellValue("Ngày Tạo");
        header.createCell(7).setCellValue("Ngày Sửa");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        Row row = sheet.createRow(1);
        row.createCell(0).setCellValue(nv.getId().toString());
        row.createCell(1).setCellValue(nv.getTaiKhoanFE());
        row.createCell(2).setCellValue(nv.getTaiKhoanFPT());
        row.createCell(3).setCellValue(nv.getTen());
        row.createCell(4).setCellValue(nv.getMaNhanVien());
        row.createCell(5).setCellValue(nv.getTrangThai() == 1 ? "Hoạt Động" : "Không Hoạt Động");
        row.createCell(6).setCellValue(sdf.format(new Date(nv.getNgayTao())));
        row.createCell(7).setCellValue(sdf.format(new Date(nv.getNgaySua())));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=employee_" + nv.getMaNhanVien() + ".xlsx")
                .body(resource);
    }
}


