package org.example.testthuctap.Repository;

import org.example.testthuctap.Model.ChuyenNganh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChuyenNganhRepo extends JpaRepository<ChuyenNganh, UUID> {
}
