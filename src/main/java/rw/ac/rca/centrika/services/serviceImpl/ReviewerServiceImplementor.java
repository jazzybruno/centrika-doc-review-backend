package rw.ac.rca.centrika.services.serviceImpl;

import rw.ac.rca.centrika.dtos.CreateReviewerDTO;
import rw.ac.rca.centrika.models.Reviewer;
import rw.ac.rca.centrika.services.ReviewerService;

import java.util.List;
import java.util.UUID;

public class ReviewerServiceImplementor implements ReviewerService {
    @Override
    public List<Reviewer> findAll() {
        return null;
    }

    @Override
    public Reviewer findById(UUID reviewerId) {
        return null;
    }

    @Override
    public Reviewer createReviewer(CreateReviewerDTO createReviewerDTO) {
        return null;
    }

    @Override
    public Reviewer updateReviewer(UUID reviewerId, CreateReviewerDTO updateReviewerDTO) {
        return null;
    }

    @Override
    public Boolean deleteReviewer(UUID reviewerId) {
        return null;
    }

    @Override
    public List<Reviewer> findByDocumentReviewId(UUID documentReviewId) {
        return null;
    }
}
