package rw.ac.rca.centrika.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rw.ac.rca.centrika.enumerations.ERefNumStatus;
import rw.ac.rca.centrika.models.Department;
import rw.ac.rca.centrika.models.ReferenceNumber;
import rw.ac.rca.centrika.models.User;

import java.util.List;
import java.util.UUID;

public interface IReferenceNumberRepository extends JpaRepository<ReferenceNumber, UUID> {
    @Query("SELECT r FROM ReferenceNumber r WHERE r.createdBy.department = ?1")
    List<ReferenceNumber> findAllByDepartment(Department department);

    @Query("SELECT r FROM ReferenceNumber r WHERE r.destination like %?1%")
    List<ReferenceNumber> searchByDestination(String destination);

    List<ReferenceNumber> findAllByStatus(ERefNumStatus status);

    List<ReferenceNumber> findAllByCreatedBy(User user);
}