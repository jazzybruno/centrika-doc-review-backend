package rw.ac.rca.centrika.services;

import rw.ac.rca.centrika.dtos.CreateReviewerDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateUserDepartmentDTO;
import rw.ac.rca.centrika.models.Reviewer;

import java.util.List;
import java.util.UUID;

public interface ReviewerService {
    // CRUD methods here
     public List<Reviewer> findAll();
        public Reviewer findReviewerById(UUID reviewerId);
        public Reviewer createReviewer(CreateReviewerDTO createReviewerDTO);
        public Reviewer updateReviewer(UUID reviewerId, CreateReviewerDTO updateReviewerDTO);
        public Boolean deleteReviewer(UUID reviewerId);

    // Custom methods here

        public List<Reviewer> findByDocumentReviewId(UUID documentReviewId);
        public List<Reviewer> findByUserId(UUID userId);

}
