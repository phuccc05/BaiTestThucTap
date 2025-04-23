package org.example.testthuctap.Repository;

import org.example.testthuctap.Model.CoSo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CoSoRepository extends JpaRepository<CoSo, UUID> {
}