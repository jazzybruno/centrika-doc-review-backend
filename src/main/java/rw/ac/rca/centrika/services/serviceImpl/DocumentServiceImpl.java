package rw.ac.rca.centrika.services.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import rw.ac.rca.centrika.dtos.requests.CreateDocumentDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateDocumentDTO;
import rw.ac.rca.centrika.enumerations.ECategory;
import rw.ac.rca.centrika.enumerations.EDocStatus;
import rw.ac.rca.centrika.enumerations.ERelated;
import rw.ac.rca.centrika.enumerations.ERelationType;
import rw.ac.rca.centrika.exceptions.BadRequestAlertException;
import rw.ac.rca.centrika.exceptions.InternalServerErrorException;
import rw.ac.rca.centrika.exceptions.NotFoundException;
import rw.ac.rca.centrika.models.*;
import rw.ac.rca.centrika.repositories.IDepartmentRepository;
import rw.ac.rca.centrika.repositories.IDocumentRelationRepository;
import rw.ac.rca.centrika.repositories.IDocumentRepository;
import rw.ac.rca.centrika.repositories.IDocumentReviewRepository;
import rw.ac.rca.centrika.services.DocumentService;
import rw.ac.rca.centrika.services.ReferenceNumberService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentServiceImpl implements DocumentService {
    private final IDocumentRepository documentRepository;
    private final UserServiceImpl userService;
    private final IDepartmentRepository departmentRepository;
    private final  FileServiceImpl  fileService;
    private final IDocumentReviewRepository documentReviewRepository;
    private final ReferenceNumberService referenceNumberService;
    private final IDocumentRelationRepository documentRelationRepository;
    @Autowired
    public DocumentServiceImpl(IDocumentRepository documentRepository, UserServiceImpl userService, IDepartmentRepository departmentRepository, FileServiceImpl fileService, IDocumentReviewRepository documentReviewRepository, ReferenceNumberService referenceNumberService, IDocumentRelationRepository documentRelationRepository) {
        this.documentRepository = documentRepository;
        this.userService = userService;
        this.departmentRepository = departmentRepository;
        this.fileService = fileService;
        this.documentReviewRepository = documentReviewRepository;
        this.referenceNumberService = referenceNumberService;
        this.documentRelationRepository = documentRelationRepository;
    }

    @Override
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    @Override
    public List<Document> getDocumentByUser(UUID userId) {
        User user = userService.getUserById(userId);
         return documentRepository.findAllByCreatedBy(user);
    }
    @Override
    public List<Document> getDocumentByDepartment(UUID deptId) {
        Department department = departmentRepository.findById(deptId).orElseThrow(() -> {throw new NotFoundException("The Department was not found");
        });
        return documentRepository.findAllByDepartment(department);
    }

    @Override
    public Document getDocumentById(UUID doc_id) {
        return documentRepository.findById(doc_id).orElseThrow(()-> {throw new NotFoundException("The document was not found");});
    }

    @Override
    public Document createDocument(MultipartFile docFile ,  CreateDocumentDTO createDocumentDTO) throws IOException {
        Document document = new Document();
        if(createDocumentDTO.getCategory().equals(ECategory.EXTERNAL)){
            // if it is external then the reference number is a must
            if(createDocumentDTO.getReferenceNumberId().isEmpty()){
                throw new NotFoundException("The reference number is required for and external document");
            }else {
                ReferenceNumber referenceNumber = referenceNumberService.getReferenceNumberById(createDocumentDTO.getReferenceNumberId().get());
                if(documentRepository.existsByReferenceNumber(referenceNumber)){
                    throw new BadRequestAlertException("The reference number is already used");
                }
                document.setReferenceNumber(referenceNumber);
            }

        }else{
            // if it is internal then the reference number is not a must
            document.setReferenceNumber(null);
        }
        User user = userService.getUserById(createDocumentDTO.getCreator());
        String fileName = fileService.uploadFile(docFile);
        EDocStatus status = EDocStatus.PENDING;
        ECategory category = createDocumentDTO.getCategory();
        document.setTitle(createDocumentDTO.getTitle());
        document.setDescription(createDocumentDTO.getDescription());
        document.setFileUrl(fileName);
        document.setCategory(category);
        document.setStatus(status);
        document.setCreatedBy(user);
        document.setCreatedAt(new Date());


        try {
              Document savedDoc = documentRepository.save(document);

          if(createDocumentDTO.getIsRelated().equals(ERelated.RELATED)){
                  if(createDocumentDTO.getRelationType().isEmpty()){
                      throw new BadRequestAlertException("The relation type is required");
                  }
                  if(createDocumentDTO.getParentDocumentId().isEmpty()){
                      throw new BadRequestAlertException("The parent document is required");
                  }
                  Document parentDocument = documentRepository.findById(createDocumentDTO.getParentDocumentId().get()).orElseThrow(()-> {throw new NotFoundException("The parent document was not found");});
                  DocumentRelation documentRelation = new DocumentRelation();
                  documentRelation.setChildDocument(savedDoc);
                  documentRelation.setParentDocument(parentDocument);
                  documentRelation.setRelationType(createDocumentDTO.getRelationType().get());
                  documentRelationRepository.save(documentRelation);
              }

            return document;
          }catch (Exception e){
              throw new InternalServerErrorException(e.getMessage());
          }
    }

    @Override
    @Transactional
    public Document updatedocument(UUID doc_id, UpdateDocumentDTO updateDocumentDTO) {
        Document document = documentRepository.findById(doc_id).orElseThrow(()-> {throw new NotFoundException("The document was not found");});
        try {
            document.setTitle(updateDocumentDTO.getTitle());
            document.setDescription(updateDocumentDTO.getDescription());
            return document;
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Document updateDocFile(UUID docId, MultipartFile docFile)  {
            Document document = documentRepository.findById(docId).orElseThrow(()-> {throw new NotFoundException("The document was not found");});
            String normalName = document.getFileUrl();
            try {
                String fileName = fileService.updateFile(normalName , docFile);
                document.setFileUrl(fileName);
                return document;
            } catch (Exception e) {
                throw new InternalServerErrorException(e.getMessage());
            }
    }

    @Override
    public Document deleteDocument(UUID doc_id) {
        Document document = documentRepository.findById(doc_id).orElseThrow(()-> {throw new NotFoundException("The document was not found");});
        try {
            documentRepository.deleteById(doc_id);
            return document;
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public Document getDocumentByReferenceNumber(UUID referenceNumberId) {
        ReferenceNumber referenceNumber = referenceNumberService.getReferenceNumberById(referenceNumberId);
        try {
            return documentRepository.findAllByReferenceNumber(referenceNumber);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Document updateReferenceNumber(UUID docId, UUID referenceNumberId) {
        ReferenceNumber referenceNumber = referenceNumberService.getReferenceNumberById(referenceNumberId);
        Document document = documentRepository.findById(docId).orElseThrow(()-> {throw new NotFoundException("The document was not found");});
        try {
            document.setReferenceNumber(referenceNumber);
            return document;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Document updateDocumentStatus(UUID docId, EDocStatus status) {
        Document document = documentRepository.findById(docId).orElseThrow(()-> {throw new NotFoundException("The document was not found");});
        try {
            document.setStatus(status);
            return document;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<Document> getDocumentsRequestedToMe(UUID userId) {
        User user = userService.getUserById(userId);
        try {
            List<DocumentReview> documentReviews =  documentReviewRepository.getDocumentsRequestedToMe(user);
            List<Document> documents = new ArrayList<>();
            for (DocumentReview documentReview : documentReviews) {
                documents.add(documentReview.getDocument());
            }
            return documents;
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<Document> getDocumentRequestedByMe(UUID userId) {
        User user = userService.getUserById(userId);
        try {
            List<DocumentReview> documentReviews =  documentReviewRepository.getDocumentRequestedByMe(user);
            List<Document> documents = new ArrayList<>();
            for (DocumentReview documentReview : documentReviews) {
                documents.add(documentReview.getDocument());
            }
            return documents;
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

}
