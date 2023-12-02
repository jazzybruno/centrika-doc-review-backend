package rw.ac.rca.centrika.services.serviceImpl;

import rw.ac.rca.centrika.dtos.CreateReviewActionDTO;
import rw.ac.rca.centrika.models.ReviewAction;
import rw.ac.rca.centrika.services.ReviewActionService;

import java.util.List;
import java.util.UUID;

public class ReviewActionServiceImpl  implements ReviewActionService {
    @Override
    public List<ReviewAction> findAll() {
        return null;
    }

    @Override
    public ReviewAction findById(UUID reviewActionId) {
        return null;
    }

    @Override
    public ReviewAction save(CreateReviewActionDTO reviewActionDTO) {
        return null;
    }

    @Override
    public ReviewAction update(UUID reviewActionId, CreateReviewActionDTO reviewActionDTO) {
        return null;
    }

    @Override
    public void delete(UUID reviewActionId) {

    }

    @Override
    public List<ReviewAction> findByReviewerId(UUID reviewerId) {
        return null;
    }

    @Override
    public List<ReviewAction> findByDocumentId(UUID documentId) {
        return null;
    }

    @Override
    public List<ReviewAction> findByReviewerIdAndDocumentId(UUID reviewerId, UUID documentId) {
        return null;
    }

    @Override
    public List<ReviewAction> findByReviewerIdAndDocumentIdAndStatus(UUID reviewerId, UUID documentId, String status) {
        return null;
    }

    @Override
    public List<ReviewAction> findByReviewerIdAndStatus(UUID reviewerId, String status) {
        return null;
    }

    @Override
    public List<ReviewAction> findByDocumentReviewId(UUID documentReviewId) {
        return null;
    }

    @Override
    public List<ReviewAction> findByDocumentReviewIdAndStatus(UUID documentReviewId, String status) {
        return null;
    }

    @Override
    public List<ReviewAction> findByDocumentReviewIdAndReviewerId(UUID documentReviewId, UUID reviewerId) {
        return null;
    }
}
