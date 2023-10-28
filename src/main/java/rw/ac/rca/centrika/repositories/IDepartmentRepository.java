package rw.ac.rca.centrika.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.rca.centrika.models.Department;

import java.util.UUID;

public interface IDepartmentRepository extends JpaRepository<Department, UUID> {
}