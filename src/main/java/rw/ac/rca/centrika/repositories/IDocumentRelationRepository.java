package rw.ac.rca.centrika.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.rca.centrika.enumerations.ERelationType;
import rw.ac.rca.centrika.models.Document;
import rw.ac.rca.centrika.models.DocumentRelation;

import java.util.List;
import java.util.UUID;

public interface IDocumentRelationRepository extends JpaRepository<DocumentRelation, UUID> {
    List<DocumentRelation> findAllByRelationType(ERelationType relationType);
    List<DocumentRelation> findAllByParentDocumentId(Document documentId);
    List<DocumentRelation> findAllByChildDocumentId(Document documentId);
}