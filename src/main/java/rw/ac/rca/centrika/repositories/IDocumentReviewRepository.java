package rw.ac.rca.centrika.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rw.ac.rca.centrika.models.DocumentReview;

import javax.print.Doc;
import java.util.List;
import java.util.UUID;

public interface IDocumentReviewRepository extends JpaRepository<DocumentReview, UUID> {
    @Query(nativeQuery = true , value = "SELECT  dr.* FROM document_review dr where dr.user_id = ?1")
    List<DocumentReview> getAllDocumentsReviewByCreator(UUID creatorId);
    @Query(nativeQuery = true , value = "SELECT dr.* FROM document_review dr INNER JOIN user_reviews usr ON dr.id = usr.document_id WHERE usr.user_id = ?1")
    List<DocumentReview> getAllDocumentsReviewByReviewer(UUID reviwerId);
}