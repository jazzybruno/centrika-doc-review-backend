package rw.ac.rca.centrika.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.rca.centrika.models.DocumentReview;
import rw.ac.rca.centrika.models.Reviewer;
import rw.ac.rca.centrika.models.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IReviewerRepository extends JpaRepository<Reviewer, UUID> {
    Optional<Reviewer> findByUserAndDocumentReview(User user , DocumentReview documentReview);
    List<Reviewer> findAllByDocumentReview(DocumentReview documentReview);
    List<Reviewer> findAllByUser(User user);
}