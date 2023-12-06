package rw.ac.rca.centrika.services.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.rca.centrika.dtos.CreateDocumentRelationDTO;
import rw.ac.rca.centrika.enumerations.ERelationType;
import rw.ac.rca.centrika.exceptions.InternalServerErrorException;
import rw.ac.rca.centrika.models.Document;
import rw.ac.rca.centrika.models.DocumentRelation;
import rw.ac.rca.centrika.repositories.IDocumentRelationRepository;
import rw.ac.rca.centrika.repositories.IDocumentRepository;
import rw.ac.rca.centrika.services.DocumentRelationService;

import java.util.List;
import java.util.UUID;

@Service
public class DocumentRelationServiceImpl implements DocumentRelationService {
    private final IDocumentRelationRepository documentRelationRepository;
    private final IDocumentRepository documentRepository;
    private DocumentRelation documentRelation;

    @Autowired
    public DocumentRelationServiceImpl(IDocumentRelationRepository documentRelationRepository, IDocumentRepository documentRepository) {
        this.documentRelationRepository = documentRelationRepository;
        this.documentRepository = documentRepository;
    }

    @Override
    public List<DocumentRelation> getAllDocumentRelations() {
        try {
            return documentRelationRepository.findAll();
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public DocumentRelation getDocumentRelationById(UUID documentRelationId) {
        try {
            return documentRelationRepository.findById(documentRelationId).orElseThrow(() -> new InternalServerErrorException("Document relation not found"));
        }catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public DocumentRelation createDocumentRelation(CreateDocumentRelationDTO documentRelationDTO) {
        Document parent_doc = documentRepository.findById(documentRelationDTO.getParentDocumentId()).orElseThrow(() -> new InternalServerErrorException("Parent document not found"));
        Document child_doc = documentRepository.findById(documentRelationDTO.getChildDocumentId()).orElseThrow(() -> new InternalServerErrorException("Child document not found"));
        documentRelation = new DocumentRelation();
        documentRelation.setParentDocument(parent_doc);
        documentRelation.setChildDocument(child_doc);
        documentRelation.setRelationType(documentRelationDTO.getRelationType());
        try {
            return documentRelationRepository.save(documentRelation);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public DocumentRelation updateDocumentRelation(UUID documentRelationId, CreateDocumentRelationDTO documentRelationDTO) {
        documentRelation = documentRelationRepository.findById(documentRelationId).orElseThrow(() -> new InternalServerErrorException("Document relation not found"));
        Document parent_doc = documentRepository.findById(documentRelationDTO.getParentDocumentId()).orElseThrow(() -> new InternalServerErrorException("Parent document not found"));
        Document child_doc = documentRepository.findById(documentRelationDTO.getChildDocumentId()).orElseThrow(() -> new InternalServerErrorException("Child document not found"));
           try {
               documentRelation.setParentDocument(parent_doc);
               documentRelation.setChildDocument(child_doc);
               documentRelation.setRelationType(documentRelationDTO.getRelationType());
               return documentRelation;
           }catch (Exception e){
               throw new InternalServerErrorException(e.getMessage());
           }

    }

    @Override
    public DocumentRelation deleteDocumentRelation(UUID documentRelationId) {
        documentRelation = documentRelationRepository.findById(documentRelationId).orElseThrow(() -> new InternalServerErrorException("Document relation not found"));
        try {
            documentRelationRepository.delete(documentRelation);
            return documentRelation;
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<DocumentRelation> getAllDocRelationsByRelationType(ERelationType relationType) {
        try {
            return documentRelationRepository.findAllByRelationType(relationType);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<DocumentRelation> getAllDocRelationByParentDoc(UUID documentId) {
        Document document = documentRepository.findById(documentId).orElseThrow(() -> new InternalServerErrorException("Document not found"));
        try {
            return documentRelationRepository.findAllByParentDocument(document);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<DocumentRelation> getAllDocRelationByChildDocument(UUID documentId) {
        Document document = documentRepository.findById(documentId).orElseThrow(() -> new InternalServerErrorException("Document not found"));
        try {
            return documentRelationRepository.findAllByChildDocument(document);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<DocumentRelation> getAllPredecessorsByRelationType(UUID documentId, ERelationType relationType) {
        Document document = documentRepository.findById(documentId).orElseThrow(() -> new InternalServerErrorException("Document with id : " + documentId + "  not found"));
        try{
          return null;
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<DocumentRelation> getAllSuccessorsByRelationType(UUID documentId, ERelationType relationType) {
        return null;
    }
}
