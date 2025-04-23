package org.example.testthuctap.Repository;

import org.example.testthuctap.Model.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, UUID> {
    boolean existsByMaNhanVien(String maNhanVien);
    boolean existsByTaiKhoanFPT(String taiKhoanFPT);
    boolean existsByTaiKhoanFE(String taiKhoanFE);
}
