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

    @Override
    @Transactional
    public DocumentReview reviewTheDocument(ReviewDocumentDTO reviewDocumentDTO) {
        DocumentReview documentReview = this.getDocumentReviewById(reviewDocumentDTO.getReviewDocId());
        User user = userService.getUserById(reviewDocumentDTO.getReviewer());
        String rejectNotificationMessage = "The Document was rejected by: " + user.getUsername() + " check the comments to fix the document";
        String approveNotificationMessage = "The Document was approved by: " + user.getUsername();
        Notification notification = new Notification();
        Comment comment = new Comment(
           reviewDocumentDTO.getCommentContent(),
                new Date(),
                user,
                documentReview
        );
          try {
              if(reviewDocumentDTO.getStatus().equals(EReviewStatus.REJECT)){
                  documentReview.setStatus(EDocStatus.REJECTED);
                  notification.setCreatedAt(new Date());
                  notification.setUser(documentReview.getReviewDoc().getCreatedBy());
                  notification.setMessage(rejectNotificationMessage);
                  notification.setRead(false);
                  notificationRepository.save(notification);
                  commentRepository.save(comment);
              }else if (reviewDocumentDTO.getStatus().equals(EReviewStatus.APPROVE)){
                  documentReview.setStatus(EDocStatus.APPROVED);
                  notification.setCreatedAt(new Date());
                  notification.setUser(documentReview.getReviewDoc().getCreatedBy());
                  notification.setMessage(approveNotificationMessage);
                  notification.setRead(false);
                  notificationRepository.save(notification);
                  commentRepository.save(comment);
              }else{
                  throw new BadRequestAlertException("The status must be REJECT or APPROVE");
              }
              return documentReview;
          }catch (Exception e){
              e.printStackTrace();
              throw new InternalServerErrorException(e.getMessage());
          }
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
}
