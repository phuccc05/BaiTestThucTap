package org.example.testthuctap.Controller;

import lombok.RequiredArgsConstructor;
import org.example.testthuctap.Model.BoMonCoSo;
import org.example.testthuctap.Model.ChuyenNganhBoMonCoSo;
import org.example.testthuctap.Model.CoSo;
import org.example.testthuctap.Model.GanChuyenNganhRequest;
import org.example.testthuctap.Service.ChuyenNganhNhanVienService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/chuyen-nganh-nhan-vien")
@RequiredArgsConstructor
public class ChuyenNganhNhanVienController {

    private final ChuyenNganhNhanVienService chuyenNganhNhanVienService;

    @GetMapping("/co-so")
    public ResponseEntity<List<CoSo>> layDanhSachCoSo() {
        return ResponseEntity.ok(chuyenNganhNhanVienService.layDanhSachCoSo());
    }

    @GetMapping("/bo-mon")
    public ResponseEntity<List<BoMonCoSo>> layBoMonTheoCoSo(@RequestParam UUID coSoId) {
        return ResponseEntity.ok(chuyenNganhNhanVienService.layBoMonTheoCoSo(coSoId));
    }

    @GetMapping("/chuyen-nganh")
    public ResponseEntity<List<ChuyenNganhBoMonCoSo>> layChuyenNganhTheoBoMonCoSo(@RequestParam UUID boMonCoSoId) {
        return ResponseEntity.ok(chuyenNganhNhanVienService.layChuyenNganhTheoBoMonCoSo(boMonCoSoId));
    }

    @PostMapping("/gan")
    public ResponseEntity<String> ganChuyenNganhChoNhanVien(@RequestBody GanChuyenNganhRequest request) {
        boolean thanhCong = chuyenNganhNhanVienService.ganChuyenNganhChoNhanVien(
                request.getNhanVienId(),
                request.getChuyenNganhBoMonCoSoId()
        );

        if (thanhCong) {
            return ResponseEntity.ok("Gán chuyên ngành thành công.");
        } else {
            return ResponseEntity.badRequest().body("Nhân viên đã có chuyên ngành trong cơ sở này.");
        }
    }
}
