package rw.ac.rca.centrika.services;

import rw.ac.rca.centrika.dtos.CreateDocumentRelationDTO;
import rw.ac.rca.centrika.enumerations.ERelationType;
import rw.ac.rca.centrika.models.DocumentRelation;

import java.util.List;
import java.util.UUID;

public interface DocumentRelationService {
    // CRUD Methods - Create, Read, Update, Delete
     public List<DocumentRelation> getAllDocumentRelations();
        public DocumentRelation getDocumentRelationById(UUID documentRelationId);
        public DocumentRelation createDocumentRelation(CreateDocumentRelationDTO documentRelationDTO);
        public DocumentRelation updateDocumentRelation(UUID documentRelationId, CreateDocumentRelationDTO documentRelationDTO);
        public DocumentRelation deleteDocumentRelation(UUID documentRelationId);

    // Custom Methods
       public List<DocumentRelation> getAllDocRelationsByRelationType(ERelationType relationType);
       public List<DocumentRelation> getAllDocRelationByParentDoc(UUID documentId);
        public List<DocumentRelation> getAllDocRelationByChildDocument(UUID documentId);
}
