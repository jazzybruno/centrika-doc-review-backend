package rw.ac.rca.centrika.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.rca.centrika.models.DocumentReview;

import java.util.UUID;

public interface IDocumentReviewRepository extends JpaRepository<DocumentReview, UUID> {
}