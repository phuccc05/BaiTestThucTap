package org.example.testthuctap.Repository;

import org.example.testthuctap.Model.ChuyenNganhBoMonCoSo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChuyenNganhBoMonCoSoRepository extends JpaRepository<ChuyenNganhBoMonCoSo, UUID> {

    List<ChuyenNganhBoMonCoSo> findByBoMonCoSo_Id(UUID boMonCoSoId);
}
