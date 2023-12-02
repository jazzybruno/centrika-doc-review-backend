package rw.ac.rca.centrika.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.rca.centrika.models.ReviewAction;

import java.util.UUID;

public interface ReviewActionRepository extends JpaRepository<ReviewAction, UUID> {
}