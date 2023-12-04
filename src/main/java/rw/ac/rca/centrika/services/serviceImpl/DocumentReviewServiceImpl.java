package rw.ac.rca.centrika.services.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import rw.ac.rca.centrika.dtos.requests.*;
import rw.ac.rca.centrika.enumerations.EDocStatus;
import rw.ac.rca.centrika.enumerations.EReviewStatus;
import rw.ac.rca.centrika.exceptions.BadRequestAlertException;
import rw.ac.rca.centrika.exceptions.InternalServerErrorException;
import rw.ac.rca.centrika.exceptions.NotFoundException;
import rw.ac.rca.centrika.models.*;
import rw.ac.rca.centrika.repositories.ICommentRepository;
import rw.ac.rca.centrika.repositories.IDocumentReviewRepository;
import rw.ac.rca.centrika.repositories.INotificationRepository;
import rw.ac.rca.centrika.services.DocumentReviewService;

import javax.print.Doc;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DocumentReviewServiceImpl implements DocumentReviewService {
    private IDocumentReviewRepository documentReviewRepository;
    private DocumentServiceImpl documentService;
    private UserServiceImpl userService;
    private INotificationRepository notificationRepository;
    private ICommentRepository commentRepository;
    @Autowired
    public DocumentReviewServiceImpl(IDocumentReviewRepository documentReviewRepository, DocumentServiceImpl documentService, UserServiceImpl userService , INotificationRepository notificationRepository ,  ICommentRepository commentRepository) {
        this.documentReviewRepository = documentReviewRepository;
        this.documentService = documentService;
        this.userService = userService;
        this.notificationRepository = notificationRepository;
        this.commentRepository = commentRepository;
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
    @Transactional
    public DocumentReview requestDocumentReview(RequestReviewDTO requestReviewDTO) throws IOException {
        Document document = documentService.getDocumentById(requestReviewDTO.getDocumentId());
        User createdBy = userService.getUserById(requestReviewDTO.getCreatedBy());
        Department sendingDepartment = createdBy.getDepartment();
        Date createdAt = new Date();
        Date expectedCompleteTime = requestReviewDTO.getExpectedCompleteTime();
        Date deadline = null;
        if (expectedCompleteTime != null){
            deadline = new Date(expectedCompleteTime.getTime() - 86400000);
        }
        DocumentReview documentReview = new DocumentReview(
                document,
                sendingDepartment,
                createdBy,
                expectedCompleteTime,
                deadline,
                createdAt,
                null
        );

        try {
            DocumentReview review = documentReviewRepository.save(documentReview);
            for (UUID user : requestReviewDTO.getReviewers()) {
                User user1 = userService.getUserById(user);
                String message = "You have a new document review from: " + createdBy.getUsername();
                Notification notification = new Notification(
                        createdBy,
                        user1,
                        message
                );
                notificationRepository.save(notification);
            }
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
        User user1 = userService.getUserById(updateDocumentReviewDTO.getCreator());
        try {
            Set<User> users = documentReview.getReviewers();
            users.add(user);
            documentReview.setReviewers(users);
            documentReview.setStatus(EDocStatus.PENDING);
            documentReview.setUpdatedAt(new Date());
            CreateDocumentDTO createDocumentDTO = new CreateDocumentDTO(
                    updateDocumentReviewDTO.getTitle(),
                    updateDocumentReviewDTO.getDescription(),
                    updateDocumentReviewDTO.getCategory(),
                    updateDocumentReviewDTO.getDepartmentId(),
                    updateDocumentReviewDTO.getCreator(),
                    documentReview.getId()
            );
            Document document = documentService.createDocument(file , createDocumentDTO);
            documentReview.setCurrentDocument(document.getId());
            String message =  "You have an updated document review from: " +  user1.getUsername();
            Notification notification = new Notification(
                    user,
                    message,
                    false
            );
            Date date = new Date();
            notification.setCreatedAt(date);
            notificationRepository.save(notification);
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
    public List<DocumentReview> getDocumentReviewByCreator(UUID creatorId) {
        return null;
    }



    @Override
    public DocumentReview forwardTheDocument(ForwardDocumentDTO forwardDocumentDTO) {
        DocumentReview documentReview = this.getDocumentReviewById(forwardDocumentDTO.getReviewDocId());
        User user = userService.getUserById(forwardDocumentDTO.getReviewer());
        String forwardNotificationMessage = "The Document was forwarded to you for review by: " + user.getUsername();
        Notification notification = new Notification();
        Comment comment = new Comment(
                forwardDocumentDTO.getCommentContent(),
                new Date(),
                user,
                documentReview
        );
       try {
           User newReviewer = userService.getUserById(forwardDocumentDTO.getNewReviewerId());
           Set<User> users = documentReview.getReviewers();
           users.add(newReviewer);
           documentReview.setReviewers(users);
           notification.setCreatedAt(new Date());
           notification.setMessage(forwardNotificationMessage);
           notification.setUser(newReviewer);
           notification.setRead(false);
           notificationRepository.save(notification);
           commentRepository.save(comment);
           return documentReview;
       }catch (Exception e){
           throw new InternalServerErrorException(e.getMessage());
       }
    }

    @Override
    public List<DocumentReview> findDocumentReviewByDocumentId(UUID documentId) {
        return null;
    }

    @Override
    public List<DocumentReview> getDocumentReviewBySenderDepartment(UUID senderDepartmentId) {
        return null;
    }

    @Override
    public List<DocumentReview> getDocumentReviewByReceiverDepartment(UUID receiverDepartmentId) {
        return null;
    }

}
