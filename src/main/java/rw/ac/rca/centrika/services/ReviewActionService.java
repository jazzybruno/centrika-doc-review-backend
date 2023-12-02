package rw.ac.rca.centrika.services;

import rw.ac.rca.centrika.dtos.CreateReviewActionDTO;
import rw.ac.rca.centrika.models.ReviewAction;

import java.util.List;
import java.util.UUID;

public interface ReviewActionService {
    // CRUD methods
    public List<ReviewAction> findAll();
    public ReviewAction findById(UUID reviewActionId);
    public ReviewAction save(CreateReviewActionDTO reviewActionDTO);
    public ReviewAction update(UUID reviewActionId , CreateReviewActionDTO reviewActionDTO);
    public void delete(UUID reviewActionId);

    // Custom methods
    public List<ReviewAction> findByReviewerId(UUID reviewerId);
    public List<ReviewAction> findByDocumentId(UUID documentId);
    public List<ReviewAction> findByReviewerIdAndDocumentId(UUID reviewerId, UUID documentId);
    public List<ReviewAction> findByReviewerIdAndDocumentIdAndStatus(UUID reviewerId, UUID documentId, String status);
    public List<ReviewAction> findByReviewerIdAndStatus(UUID reviewerId, String status);
    List<ReviewAction> findByDocumentReviewId(UUID documentReviewId);
    List<ReviewAction> findByDocumentReviewIdAndStatus(UUID documentReviewId, String status);
    List<ReviewAction> findByDocumentReviewIdAndReviewerId(UUID documentReviewId, UUID reviewerId);
}
