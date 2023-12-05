package rw.ac.rca.centrika.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rw.ac.rca.centrika.enumerations.EReviewStatus;
import rw.ac.rca.centrika.models.Document;
import rw.ac.rca.centrika.models.DocumentReview;
import rw.ac.rca.centrika.models.ReviewAction;
import rw.ac.rca.centrika.models.Reviewer;

import java.util.List;
import java.util.UUID;

public interface IReviewActionRepository extends JpaRepository<ReviewAction, UUID> {
    List<ReviewAction> findAllByReviewer(Reviewer reviewer);
    List<ReviewAction> findAllByReviewerAndAction(Reviewer reviewer , EReviewStatus status);
    List<ReviewAction> findAllByDocumentReview(DocumentReview documentReview);
    List<ReviewAction> findAllByDocumentReviewAndAction(DocumentReview documentReview , EReviewStatus status);
    List<ReviewAction> findAllByDocumentReviewAndReviewer(DocumentReview documentReview , Reviewer reviewer);

    @Query("SELECT DISTINCT ra FROM ReviewAction ra JOIN ra.documentReview dr JOIN dr.document d WHERE d = ?1")
    List<ReviewAction> findAllByDocument(Document document);

    @Query("SELECT DISTINCT ra FROM ReviewAction ra JOIN ra.documentReview dr JOIN dr.document d WHERE d = ?1 AND ra.reviewer = ?2")
    List<ReviewAction> findAllByReviewerAndDocument(Reviewer reviewer ,  Document document);

    @Query("SELECT DISTINCT ra FROM ReviewAction ra JOIN ra.documentReview dr JOIN dr.document d WHERE d = ?1 AND ra.reviewer = ?2 AND ra.action = ?3")
    List<ReviewAction> findAllByReviewerAndDocumentStatus(Reviewer reviewer , Document document , EReviewStatus status);

}