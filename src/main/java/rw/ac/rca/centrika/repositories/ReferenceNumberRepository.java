package rw.ac.rca.centrika.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.rca.centrika.models.ReferenceNumber;

import java.util.UUID;

public interface ReferenceNumberRepository extends JpaRepository<ReferenceNumber, UUID> {
}