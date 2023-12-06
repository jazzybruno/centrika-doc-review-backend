package rw.ac.rca.centrika.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rw.ac.rca.centrika.enumerations.ECategory;
import rw.ac.rca.centrika.enumerations.EDocStatus;
import rw.ac.rca.centrika.models.Department;
import rw.ac.rca.centrika.models.Document;
import rw.ac.rca.centrika.models.ReferenceNumber;
import rw.ac.rca.centrika.models.User;

import java.util.List;
import java.util.UUID;

public interface IDocumentRepository extends JpaRepository<Document, UUID> {
    public List<Document> findAllByCreatedBy(User user);
    public List<Document> findAllByCategory(ECategory category);

    @Query("SELECT d FROM Document d WHERE d.createdBy.department = ?1")
    List<Document> findAllByDepartment(Department department);

    Document findAllByReferenceNumber(ReferenceNumber  referenceNumber);

    boolean existsByReferenceNumber(ReferenceNumber referenceNumberId);

}