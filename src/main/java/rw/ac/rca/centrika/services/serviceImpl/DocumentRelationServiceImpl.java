package rw.ac.rca.centrika.services.serviceImpl;

import rw.ac.rca.centrika.dtos.CreateDocumentRelationDTO;
import rw.ac.rca.centrika.enumerations.ERelationType;
import rw.ac.rca.centrika.models.DocumentRelation;
import rw.ac.rca.centrika.services.DocumentRelationService;

import java.util.List;
import java.util.UUID;

public class DocumentRelationServiceImpl implements DocumentRelationService {
    @Override
    public List<DocumentRelation> getAllDocumentRelations() {
        return null;
    }

    @Override
    public DocumentRelation getDocumentRelationById(UUID documentRelationId) {
        return null;
    }

    @Override
    public DocumentRelation createDocumentRelation(CreateDocumentRelationDTO documentRelationDTO) {
        return null;
    }

    @Override
    public DocumentRelation updateDocumentRelation(UUID documentRelationId, CreateDocumentRelationDTO documentRelationDTO) {
        return null;
    }

    @Override
    public DocumentRelation deleteDocumentRelation(UUID documentRelationId) {
        return null;
    }

    @Override
    public List<DocumentRelation> getAllDocRelationsByRelationType(ERelationType relationType) {
        return null;
    }

    @Override
    public List<DocumentRelation> getAllDocVersionRelations(UUID documentId) {
        return null;
    }

    @Override
    public List<DocumentRelation> getAllDocResponseRelations(UUID documentId) {
        return null;
    }
}
