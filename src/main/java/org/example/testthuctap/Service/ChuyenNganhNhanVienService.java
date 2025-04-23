package org.example.testthuctap.Service;

import lombok.RequiredArgsConstructor;
import org.example.testthuctap.Model.*;
import org.example.testthuctap.Repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChuyenNganhNhanVienService {

    private final BoMonCoSoRepository boMonCoSoRepository;
    private final ChuyenNganhBoMonCoSoRepository chuyenNganhBoMonCoSoRepository;
    private final CoSoRepository coSoRepository;
    private final NhanVienRepository nhanVienRepository;
    private final NhanVienChuyenNganhBoMonCoSoRepository nhanVienChuyenNganhBoMonCoSoRepository;

    public List<CoSo> layDanhSachCoSo() {
        return coSoRepository.findAll();
    }

    public List<BoMonCoSo> layBoMonTheoCoSo(UUID coSoId) {
        return boMonCoSoRepository.findByCoSo_Id(coSoId);
    }

    public List<ChuyenNganhBoMonCoSo> layChuyenNganhTheoBoMonCoSo(UUID boMonCoSoId) {
        return chuyenNganhBoMonCoSoRepository.findByBoMonCoSo_Id(boMonCoSoId);
    }

    public boolean ganChuyenNganhChoNhanVien(UUID nhanVienId, UUID chuyenNganhBoMonCoSoId) {
        NhanVien nhanVien = nhanVienRepository.findById(nhanVienId).orElse(null);
        ChuyenNganhBoMonCoSo chuyenNganh = chuyenNganhBoMonCoSoRepository.findById(chuyenNganhBoMonCoSoId).orElse(null);

        if (nhanVien == null || chuyenNganh == null) return false;

        UUID coSoId = chuyenNganh.getBoMonCoSo().getCoSo().getId();

        boolean daTonTai = nhanVienChuyenNganhBoMonCoSoRepository
                .existsByNhanVien_IdAndChuyenNganhBoMonCoSo_BoMonCoSo_CoSo_Id(nhanVienId, coSoId);

        if (daTonTai) return false;

        NhanVienChuyenNganhBoMonCoSo entity = new NhanVienChuyenNganhBoMonCoSo();
        entity.setNhanVien(nhanVien);
        entity.setChuyenNganhBoMonCoSo(chuyenNganh);

        nhanVienChuyenNganhBoMonCoSoRepository.save(entity);
        return true;
    }
}
