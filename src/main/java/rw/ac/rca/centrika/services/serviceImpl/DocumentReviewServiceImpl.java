package rw.ac.rca.centrika.services.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import rw.ac.rca.centrika.dtos.requests.CreateDocumentDTO;
import rw.ac.rca.centrika.dtos.requests.CreateDocumentReviewDTO;
import rw.ac.rca.centrika.dtos.requests.RequestReviewDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateDocumentReviewDTO;
import rw.ac.rca.centrika.enumerations.EDocStatus;
import rw.ac.rca.centrika.exceptions.InternalServerErrorException;
import rw.ac.rca.centrika.exceptions.NotFoundException;
import rw.ac.rca.centrika.models.Document;
import rw.ac.rca.centrika.models.DocumentReview;
import rw.ac.rca.centrika.models.Notification;
import rw.ac.rca.centrika.models.User;
import rw.ac.rca.centrika.repositories.IDocumentReviewRepository;
import rw.ac.rca.centrika.repositories.INotificationRepository;
import rw.ac.rca.centrika.services.DocumentReviewService;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DocumentReviewServiceImpl implements DocumentReviewService {
    private IDocumentReviewRepository documentReviewRepository;
    private DocumentServiceImpl documentService;
    private UserServiceImpl userService;
    private INotificationRepository notificationRepository;
    @Autowired
    public DocumentReviewServiceImpl(IDocumentReviewRepository documentReviewRepository, DocumentServiceImpl documentService, UserServiceImpl userService , INotificationRepository notificationRepository) {
        this.documentReviewRepository = documentReviewRepository;
        this.documentService = documentService;
        this.userService = userService;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<DocumentReview> getAllDocumentReviews() {
        return documentReviewRepository.findAll();
    }

    @Override
    public DocumentReview getDocumentReviewById(UUID docReviewId) {
        return documentReviewRepository.findById(docReviewId).orElseThrow(() -> {throw new NotFoundException("The document Review was not found");
        });
    }

    @Override
    public DocumentReview requestDocumentReview(MultipartFile file,  RequestReviewDTO requestReviewDTO) throws IOException {
        CreateDocumentDTO createDocumentDTO = new CreateDocumentDTO(
                requestReviewDTO.getTitle(),
                requestReviewDTO.getDescription(),
                requestReviewDTO.getCategory(),
               requestReviewDTO.getDepartmentId(),
                requestReviewDTO.getCreator()
        );

        Document document = documentService.createDocument(file , createDocumentDTO);
        User user = userService.getUserById(requestReviewDTO.getReviewer());
        try {
            EDocStatus status=EDocStatus.PENDING;
            Date createdAt = new Date();
           Set<User> reviewers = new HashSet<User>();
           reviewers.add(user);
           DocumentReview documentReview = new DocumentReview(
                   createdAt,
                   status,
                   reviewers,
                   document
           );
           String message =  "You have a new document review requested from: " +  document.getCreatedBy().getUsername();
            Notification notification = new Notification(
                    user,
                    message,
                    false
            );
            Date date = new Date();
            notification.setCreatedAt(date);
            notificationRepository.save(notification);
           documentReviewRepository.save(documentReview);
           return documentReview;
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public DocumentReview updateDocumentReview(UUID docReviewId, UpdateDocumentReviewDTO updateDocumentReviewDTO) {
        DocumentReview documentReview = documentReviewRepository.findById(docReviewId).orElseThrow(() -> {throw new NotFoundException("The document Review was not found");
        });
        User user = userService.getUserById(updateDocumentReviewDTO.getReviewer());
        try {
            Set<User> users = documentReview.getReviewers();
            users.add(user);
            documentReview.setReviewers(users);
            return documentReview;
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public DocumentReview deleteDocumentReview(UUID docReviewId) {
        DocumentReview documentReview = this.getDocumentReviewById(docReviewId);
        try {
            documentReviewRepository.deleteById(docReviewId);
            return documentReview;
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<DocumentReview> getDocumentsReviewsThatWereRequested(UUID reviewerId) {
        try {
            return documentReviewRepository.getAllDocumentsReviewByReviewer(reviewerId);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<DocumentReview> getDocumentsReviewsThatWereRequestedByUser(UUID senderId) {
        try {
            return documentReviewRepository.getAllDocumentsReviewByCreator(senderId);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
