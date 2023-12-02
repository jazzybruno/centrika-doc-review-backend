package rw.ac.rca.centrika.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.rca.centrika.models.ReviewAction;

import java.util.UUID;

public interface IReviewActionRepository extends JpaRepository<ReviewAction, UUID> {
}