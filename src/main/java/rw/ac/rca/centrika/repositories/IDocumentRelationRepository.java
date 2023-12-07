package rw.ac.rca.centrika.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rw.ac.rca.centrika.enumerations.ERelationType;
import rw.ac.rca.centrika.models.Document;
import rw.ac.rca.centrika.models.DocumentRelation;

import javax.print.Doc;
import java.util.List;
import java.util.UUID;

public interface IDocumentRelationRepository extends JpaRepository<DocumentRelation, UUID> {
    List<DocumentRelation> findAllByRelationType(ERelationType relationType);

    @Query("SELECT DISTINCT dr FROM DocumentRelation dr WHERE dr.parentDocument = ?1 OR dr.childDocument = ?1 AND dr.relationType = ?2")
    List<DocumentRelation> findAllByChildDocumentOrParentDocumentAndRelationType(Document document , ERelationType relationType);
    List<DocumentRelation> findAllByParentDocument(Document documentId);
    List<DocumentRelation> findAllByChildDocument(Document documentId);
}