package rw.ac.rca.centrika.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.rca.centrika.models.DepartmentHead;

import java.util.UUID;

public interface IDepartmentHeadRepository extends JpaRepository<DepartmentHead, UUID> {
}