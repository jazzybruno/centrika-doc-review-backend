package rw.ac.rca.centrika.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.rca.centrika.models.DocumentRelation;

import java.util.UUID;

public interface IDocumentRelationRepository extends JpaRepository<DocumentRelation, UUID> {
}