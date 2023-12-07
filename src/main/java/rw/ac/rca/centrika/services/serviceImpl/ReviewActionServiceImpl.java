package rw.ac.rca.centrika.services.serviceImpl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.ac.rca.centrika.dtos.CreateReviewActionDTO;
import rw.ac.rca.centrika.dtos.UpdateReviewActionDTO;
import rw.ac.rca.centrika.enumerations.EReviewStatus;
import rw.ac.rca.centrika.enumerations.EReviewerStatus;
import rw.ac.rca.centrika.exceptions.InternalServerErrorException;
import rw.ac.rca.centrika.exceptions.NotFoundException;
import rw.ac.rca.centrika.models.*;
import rw.ac.rca.centrika.repositories.ICommentRepository;
import rw.ac.rca.centrika.repositories.IDocumentReviewRepository;
import rw.ac.rca.centrika.repositories.INotificationRepository;
import rw.ac.rca.centrika.repositories.IReviewActionRepository;
import rw.ac.rca.centrika.services.*;

import java.beans.Transient;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewActionServiceImpl  implements ReviewActionService {
    private IReviewActionRepository reviewActionRepository;
    private ReviewerService reviewerService;
    private UserService userService;
    private IDocumentReviewRepository documentReviewRepository;
    private DocumentService documentService;
    private ICommentRepository commentRepository;
    private INotificationRepository notificationRepository;

    @Autowired
    public ReviewActionServiceImpl(INotificationRepository notificationRepository , IReviewActionRepository reviewActionRepository , ICommentRepository commentRepository, ReviewerService reviewerService, UserService userService, IDocumentReviewRepository documentReviewRepository, DocumentService documentService) {
        this.reviewActionRepository = reviewActionRepository;
        this.reviewerService = reviewerService;
        this.userService = userService;
        this.documentReviewRepository = documentReviewRepository;
        this.documentService = documentService;
        this.commentRepository = commentRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<ReviewAction> findAll() {
        try {
            return reviewActionRepository.findAll();
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public ReviewAction findById(UUID reviewActionId) {
        try {
            return reviewActionRepository.findById(reviewActionId).orElseThrow(() -> {throw new InternalServerErrorException("The review action was not found");
            });
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ReviewAction save(CreateReviewActionDTO reviewActionDTO) {
        Reviewer reviewer = reviewerService.findReviewerById(reviewActionDTO.getReviewerId());
        DocumentReview documentReview = documentReviewRepository.findById(reviewActionDTO.getDocumentReviewId()).orElseThrow(()-> {throw new NotFoundException("The Document review was not found");
        });
        ReviewAction reviewAction = new ReviewAction();
        reviewAction.setReviewer(reviewer);
        reviewAction.setDocumentReview(documentReview);
        reviewAction.setAction(reviewActionDTO.getAction());

        try {
            reviewer.setStatus(EReviewerStatus.REVIEWED);
            ReviewAction action =  reviewActionRepository.save(reviewAction);
            // Creat the comment
            Comment comment = new Comment();
            comment.setContent(reviewActionDTO.getComment());
            comment.setReviewAction(action);
            comment.setCommentCreator(reviewer.getUser());
            comment.setCreatedAt(new Date());
            commentRepository.save(comment);


            // create the notification
            Notification notification = new Notification();
            notification.setRead(false);
            notification.setCreatedAt(new Date());
            notification.setMessage("You have a new review on the document "+ documentReview.getDocument().getTitle() + " by " + reviewer.getUser().getUsername());
            notification.setSender(reviewer.getUser());
            notification.setReceiver(documentReview.getCreatedBy());
            notificationRepository.save(notification);

            return action;
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public ReviewAction update(UUID reviewActionId, UpdateReviewActionDTO updateReviewActionDTO) {
        ReviewAction reviewAction = findById(reviewActionId);
        reviewAction.setAction(updateReviewActionDTO.getAction());
        reviewAction.setUpdatedAt(new Date());
        try {
            // Creat the comment
            Comment comment = new Comment();
            comment.setContent(updateReviewActionDTO.getComment());
            comment.setReviewAction(reviewAction);
            comment.setCommentCreator(reviewAction.getReviewer().getUser());
            comment.setCreatedAt(new Date());
            commentRepository.save(comment);

            return reviewActionRepository.save(reviewAction);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public void delete(UUID reviewActionId) {
        ReviewAction action = findById(reviewActionId);
        try {
            reviewActionRepository.deleteById(reviewActionId);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<ReviewAction> findByReviewerId(UUID reviewerId) {
        Reviewer reviewer = reviewerService.findReviewerById(reviewerId);
        try {
              return reviewActionRepository.findAllByReviewer(reviewer);
        }catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<ReviewAction> findByDocumentId(UUID documentId) {
        Document document = documentService.getDocumentById(documentId);
        try {
           return reviewActionRepository.findAllByDocument(document);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<ReviewAction> findByReviewerIdAndDocumentId(UUID reviewerId, UUID documentId) {
        Document document = documentService.getDocumentById(documentId);
        Reviewer reviewer = reviewerService.findReviewerById(reviewerId);
        try {
            return reviewActionRepository.findAllByReviewerAndDocument(reviewer ,  document);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<ReviewAction> findByReviewerIdAndDocumentIdAndStatus(UUID reviewerId, UUID documentId, EReviewStatus status) {
        Document document = documentService.getDocumentById(documentId);
        Reviewer reviewer = reviewerService.findReviewerById(reviewerId);
        try {
            return reviewActionRepository.findAllByReviewerAndDocumentStatus(reviewer ,  document , status);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<ReviewAction> findByReviewerIdAndStatus(UUID reviewerId, EReviewStatus status) {
        Reviewer reviewer = reviewerService.findReviewerById(reviewerId);
        try {
            return reviewActionRepository.findAllByReviewerAndAction(reviewer , status);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<ReviewAction> findByDocumentReviewId(UUID documentReviewId) {
        DocumentReview document_review = documentReviewRepository.findById(documentReviewId).orElseThrow(()-> {throw new NotFoundException("The Document review was not found");
        });
        try {
            return reviewActionRepository.findAllByDocumentReview(document_review);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<ReviewAction> findByDocumentReviewIdAndStatus(UUID documentReviewId, EReviewStatus status) {
        DocumentReview document_review = documentReviewRepository.findById(documentReviewId).orElseThrow(()-> {throw new NotFoundException("The Document review was not found");
        });
        try {
            return reviewActionRepository.findAllByDocumentReviewAndAction(document_review , status);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<ReviewAction> findByDocumentReviewIdAndReviewerId(UUID documentReviewId, UUID reviewerId) {
        DocumentReview document_review = documentReviewRepository.findById(documentReviewId).orElseThrow(()-> {throw new NotFoundException("The Document review was not found");
        });
        Reviewer reviewer = reviewerService.findReviewerById(reviewerId);
        try {
            return reviewActionRepository.findAllByDocumentReviewAndReviewer(document_review , reviewer);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
