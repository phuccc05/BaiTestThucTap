package org.example.testthuctap.Repository;

import org.example.testthuctap.Model.NhanVienChuyenNganhBoMonCoSo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NhanVienChuyenNganhBoMonCoSoRepository extends JpaRepository<NhanVienChuyenNganhBoMonCoSo, UUID> {
//    @Query("SELECT n FROM NhanVienChuyenNganhBoMonCoSo n " +
//            "WHERE n.nhanVien.id = :nhanVienId AND n.chuyenNganhBoMonCoSo.boMonCoSo.coSo.id = :coSoId")
//    List<NhanVienChuyenNganhBoMonCoSo> findByNhanVienAndCoSo(@Param("nhanVienId") UUID nhanVienId,
//                                                             @Param("coSoId") UUID coSoId);
boolean existsByNhanVien_IdAndChuyenNganhBoMonCoSo_BoMonCoSo_CoSo_Id(UUID nhanVienId, UUID coSoId);

}
