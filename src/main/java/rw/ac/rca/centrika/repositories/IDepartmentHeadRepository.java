package rw.ac.rca.centrika.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.rca.centrika.models.Department;
import rw.ac.rca.centrika.models.DepartmentHead;
import rw.ac.rca.centrika.models.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IDepartmentHeadRepository extends JpaRepository<DepartmentHead, UUID> {
    DepartmentHead findDepartmentHeadByDepartmentId(Department department);
    Optional<DepartmentHead> findDepartmentHeadByUserId(User user);
    DepartmentHead findDepartmentHeadByDepartmentIdAndUserId(Department department, User user);
}