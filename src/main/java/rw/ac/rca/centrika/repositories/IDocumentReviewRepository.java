package rw.ac.rca.centrika.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rw.ac.rca.centrika.models.Department;
import rw.ac.rca.centrika.models.Document;
import rw.ac.rca.centrika.models.DocumentReview;
import rw.ac.rca.centrika.models.User;

import javax.print.Doc;
import java.util.List;
import java.util.UUID;

public interface IDocumentReviewRepository extends JpaRepository<DocumentReview, UUID> {
    List<DocumentReview> findAllByCreatedBy(User user);
    List<DocumentReview> findAllBySendingDepartment(Department department);
    List<DocumentReview> findAllByDocument(Document document);

    @Query("SELECT DISTINCT dr FROM DocumentReview dr JOIN Reviewer r ON r.user.department = ?1 AND r.documentReview = dr")
    List<DocumentReview> findAllByReceivingDepartment(Department department);

    @Query("SELECT DISTINCT dr FROM DocumentReview  dr WHERE dr.createdBy = ?1")
    List<DocumentReview> getDocumentRequestedByMe(User user);

    @Query("SELECT DISTINCT dr FROM DocumentReview  dr JOIN Reviewer r ON r.user = ?1 AND r.documentReview = dr")
    List<DocumentReview> getDocumentsRequestedToMe(User user);
}