package rw.ac.rca.centrika.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.rca.centrika.models.Reviewer;

import java.util.UUID;

public interface ReviewerRepository extends JpaRepository<Reviewer, UUID> {
}