package rw.ac.rca.centrika.services.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.ac.rca.centrika.dtos.CreateReviewerDTO;
import rw.ac.rca.centrika.exceptions.BadRequestAlertException;
import rw.ac.rca.centrika.exceptions.InternalServerErrorException;
import rw.ac.rca.centrika.exceptions.NotFoundException;
import rw.ac.rca.centrika.models.DocumentReview;
import rw.ac.rca.centrika.models.Reviewer;
import rw.ac.rca.centrika.models.User;
import rw.ac.rca.centrika.repositories.IDocumentReviewRepository;
import rw.ac.rca.centrika.repositories.IReviewerRepository;
import rw.ac.rca.centrika.services.DocumentReviewService;
import rw.ac.rca.centrika.services.ReviewerService;
import rw.ac.rca.centrika.services.UserService;

import java.util.*;

@Service
public class ReviewerServiceImplementor implements ReviewerService {
    private final IReviewerRepository reviewerRepository;
    private final IDocumentReviewRepository documentReviewRepository;
    private final UserService userService;
    private final String documentReviewNotFound = "Document review not found";
    private final String reviewerNotFound = "Reviewer not found";

    @Autowired
    public ReviewerServiceImplementor(IReviewerRepository reviewerRepository, IDocumentReviewRepository documentReviewRepository , UserService userService) {
        this.reviewerRepository = reviewerRepository;
        this.documentReviewRepository = documentReviewRepository;
        this.userService = userService;
    }

    @Override
    public List<Reviewer> findAll() {
        try {
            return reviewerRepository.findAll();
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public Reviewer findReviewerById(UUID reviewerId) {
        try {
            return reviewerRepository.findById(reviewerId).orElseThrow(() -> new RuntimeException(reviewerNotFound));
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
        }

    @Override
    public Reviewer createReviewer(CreateReviewerDTO createReviewerDTO) {
        User reviewerUser = userService.getUserById(createReviewerDTO.getUserId());
        DocumentReview documentReview = documentReviewRepository.findById(createReviewerDTO.getDocumentReviewId()).orElseThrow(()-> {throw new NotFoundException("The Document review was not found");
        });
        try {
            Optional<Reviewer> reviewerOptional = reviewerRepository.findByUserAndDocumentReview(reviewerUser, documentReview);
            if(reviewerOptional.isEmpty()){
                Reviewer reviewer = new Reviewer();
                reviewer.setUser(reviewerUser);
                reviewer.setDocumentReview(documentReview);
                reviewer.setCreatedAt(new Date());
                reviewer.setUpdatedAt(null);
                return reviewerRepository.save(reviewer);
            }else{
                throw new BadRequestAlertException("Reviewer already reviewed this document");
            }
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public Reviewer updateReviewer(UUID reviewerId, CreateReviewerDTO updateReviewerDTO) {
        Reviewer reviewer = findReviewerById(reviewerId);
        User reviewerUser = userService.getUserById(updateReviewerDTO.getUserId());
        DocumentReview documentReview = documentReviewRepository.findById(updateReviewerDTO.getDocumentReviewId()).orElseThrow(()-> {throw new NotFoundException("The Document review was not found");
        });
        try {
            Optional<Reviewer> reviewerOptional = reviewerRepository.findByUserAndDocumentReview(reviewerUser, documentReview);
            if (reviewerOptional.isEmpty()) {
                reviewer.setUser(reviewerUser);
                reviewer.setDocumentReview(documentReview);
                reviewer.setUpdatedAt(new Date());
                return reviewerRepository.save(reviewer);
            } else {
                throw new BadRequestAlertException("Reviewer already reviewed this document");
            }
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public Boolean deleteReviewer(UUID reviewerId) {
        Reviewer reviewer = findReviewerById(reviewerId);
        try {
            reviewerRepository.delete(reviewer);
            return true;
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<Reviewer> findByDocumentReviewId(UUID documentReviewId) {
        DocumentReview documentReview = documentReviewRepository.findById(documentReviewId).orElseThrow(()-> {throw new NotFoundException("The Document review was not found");
        });;
        try {
            return reviewerRepository.findAllByDocumentReview(documentReview);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<Reviewer> findByUserId(UUID userId) {
        User user = userService.getUserById(userId);
        try {
            return reviewerRepository.findAllByUser(user);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public Reviewer findByUserAndDocumentReview(UUID documentReviewId , UUID userId){
        User user = userService.getUserById(userId);
        DocumentReview documentReview = documentReviewRepository.findById(documentReviewId).orElseThrow(()-> {throw new NotFoundException("The Document review was not found");
        });
        try {
            Optional<Reviewer>  optionalReviewer =  reviewerRepository.findByUserAndDocumentReview(user ,  documentReview);
            return optionalReviewer.orElse(null);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
