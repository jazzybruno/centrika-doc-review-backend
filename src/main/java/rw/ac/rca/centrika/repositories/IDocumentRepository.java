package rw.ac.rca.centrika.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.rca.centrika.enumerations.ECategory;
import rw.ac.rca.centrika.enumerations.EDocStatus;
import rw.ac.rca.centrika.models.Department;
import rw.ac.rca.centrika.models.Document;
import rw.ac.rca.centrika.models.User;

import java.util.List;
import java.util.UUID;

public interface IDocumentRepository extends JpaRepository<Document, UUID> {
    public List<Document> findAllByCreatedBy(User user);
    public List<Document> findAllByDepartment(Department  department);
    public List<Document> findAllByCategory(ECategory category);
}