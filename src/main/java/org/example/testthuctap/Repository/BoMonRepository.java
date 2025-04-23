package org.example.testthuctap.Repository;

import org.example.testthuctap.Model.BoMon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BoMonRepository extends JpaRepository<BoMon, UUID> {
}
