package rw.ac.rca.centrika.services.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import rw.ac.rca.centrika.dtos.CreateReviewActionDTO;
import rw.ac.rca.centrika.dtos.CreateReviewerDTO;
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
import rw.ac.rca.centrika.services.DepartmentService;
import rw.ac.rca.centrika.services.DocumentReviewService;
import rw.ac.rca.centrika.services.ReviewActionService;
import rw.ac.rca.centrika.services.ReviewerService;

import javax.print.Doc;
import java.io.IOException;
import java.util.*;

@Service
public class DocumentReviewServiceImpl implements DocumentReviewService {
    private IDocumentReviewRepository documentReviewRepository;
    private DocumentServiceImpl documentService;
    private UserServiceImpl userService;
    private INotificationRepository notificationRepository;
    private ICommentRepository commentRepository;
    private ReviewerService reviewerService;
    private DepartmentService departmentService;
    private ReviewActionService reviewActionService;
    @Autowired
    public DocumentReviewServiceImpl(ReviewActionService reviewActionService , DepartmentService departmentService , IDocumentReviewRepository documentReviewRepository , ReviewerService reviewerService, DocumentServiceImpl documentService, UserServiceImpl userService , INotificationRepository notificationRepository ,  ICommentRepository commentRepository) {
        this.documentReviewRepository = documentReviewRepository;
        this.documentService = documentService;
        this.userService = userService;
        this.notificationRepository = notificationRepository;
        this.commentRepository = commentRepository;
        this.reviewerService = reviewerService;
        this.departmentService = departmentService;
        this.reviewActionService = reviewActionService;
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
                CreateReviewerDTO createReviewerDTO = new CreateReviewerDTO();
                createReviewerDTO.setDocumentReviewId(review.getId());
                createReviewerDTO.setUserId(user);
                reviewerService.createReviewer(createReviewerDTO);
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
        DocumentReview documentReview = this.getDocumentReviewById(docReviewId);
         try {
             DocumentReview review = documentReviewRepository.save(documentReview);
             for (UUID user : updateDocumentReviewDTO.getReviewers()) {
                Reviewer reviewer = reviewerService.findByUserAndDocumentReview(docReviewId , user);
                if(reviewer == null){
                    CreateReviewerDTO createReviewerDTO = new CreateReviewerDTO();
                    createReviewerDTO.setDocumentReviewId(review.getId());
                    createReviewerDTO.setUserId(user);
                    reviewerService.createReviewer(createReviewerDTO);
                    User user1 = userService.getUserById(user);
                    String message = "You have a new document review from: " + documentReview.getCreatedBy().getUsername();
                    Notification notification = new Notification(
                            documentReview.getCreatedBy(),
                            user1,
                            message
                    );
                    notificationRepository.save(notification);
                }
             }
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
        User user = userService.getUserById(creatorId);
        try {
            return documentReviewRepository.findAllByCreatedBy(user);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

// action so we first implement actions

    @Override
    public DocumentReview forwardTheDocument(ForwardDocumentDTO forwardDocumentDTO) {
        DocumentReview documentReview = this.getDocumentReviewById(forwardDocumentDTO.getReviewDocId());
        User user = userService.getUserById(forwardDocumentDTO.getReviewer());
        String forwardNotificationMessage = "The Document was forwarded to you for review by: " + user.getUsername();
        Notification notification = new Notification();

        // add the new reviewer in the reviewers list
        Reviewer reviewer = reviewerService.findByUserAndDocumentReview(forwardDocumentDTO.getReviewDocId() , forwardDocumentDTO.getNewReviewerId());
         if(reviewer == null){
             CreateReviewerDTO createReviewerDTO = new CreateReviewerDTO();
             createReviewerDTO.setDocumentReviewId(forwardDocumentDTO.getReviewDocId());
             createReviewerDTO.setUserId(forwardDocumentDTO.getNewReviewerId());
             reviewerService.createReviewer(createReviewerDTO);
             User user1 = userService.getUserById(forwardDocumentDTO.getNewReviewerId());
             String message = "You have a new document review from: " + user.getUsername();
             Notification notification1 = new Notification(
                     user,
                     user1,
                     message
             );
             notificationRepository.save(notification1);
         }

        // Create the action
        CreateReviewActionDTO createReviewActionDTO = new CreateReviewActionDTO(
                forwardDocumentDTO.getReviewer(),
                forwardDocumentDTO.getReviewDocId(),
                EReviewStatus.FORWARD,
                forwardDocumentDTO.getCommentContent()
        );

        this.reviewActionService.save(createReviewActionDTO);

       try {
           return documentReview;
       }catch (Exception e){
           throw new InternalServerErrorException(e.getMessage());
       }
    }

    // actions so we first implement the action

    @Override
    public List<DocumentReview> findDocumentReviewByDocumentId(UUID documentId) {
        Document document = documentService.getDocumentById(documentId);
        try {
            return documentReviewRepository.findAllByDocument(document);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<DocumentReview> getDocumentReviewBySenderDepartment(UUID senderDepartmentId) {
        Department department = departmentService.getDepartmentById(senderDepartmentId);
        try {
            return documentReviewRepository.findAllBySendingDepartment(department);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<DocumentReview> getDocumentReviewByReceiverDepartment(UUID receiverDepartmentId) {
        Department department = departmentService.getDepartmentById(receiverDepartmentId);
        try {
            return documentReviewRepository.findAllByReceivingDepartment(department);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

}
