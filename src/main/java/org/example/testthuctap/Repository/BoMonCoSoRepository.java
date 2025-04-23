package org.example.testthuctap.Repository;

import org.example.testthuctap.Model.BoMonCoSo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BoMonCoSoRepository extends JpaRepository<BoMonCoSo, UUID> {
    List<BoMonCoSo> findByCoSo_Id(UUID coSoId);
}
