package rw.ac.rca.centrika.services;

import rw.ac.rca.centrika.dtos.CreateReviewActionDTO;
import rw.ac.rca.centrika.dtos.UpdateReviewActionDTO;
import rw.ac.rca.centrika.enumerations.EReviewStatus;
import rw.ac.rca.centrika.models.ReviewAction;

import java.util.List;
import java.util.UUID;

public interface ReviewActionService {
    // CRUD methods
    public List<ReviewAction> findAll();
    public ReviewAction findById(UUID reviewActionId);
    public ReviewAction save(CreateReviewActionDTO reviewActionDTO);
    public ReviewAction update(UUID reviewActionId , UpdateReviewActionDTO updateReviewActionDTO);
    public void delete(UUID reviewActionId);

    // Custom methods
    public List<ReviewAction> findByReviewerId(UUID reviewerId);
    public List<ReviewAction> findByDocumentId(UUID documentId);
    public List<ReviewAction> findByReviewerIdAndDocumentId(UUID reviewerId, UUID documentId);
    public List<ReviewAction> findByReviewerIdAndDocumentIdAndStatus(UUID reviewerId, UUID documentId, EReviewStatus status);
    public List<ReviewAction> findByReviewerIdAndStatus(UUID reviewerId, EReviewStatus status);
    List<ReviewAction> findByDocumentReviewId(UUID documentReviewId);
    List<ReviewAction> findByDocumentReviewIdAndStatus(UUID documentReviewId, EReviewStatus status);
    List<ReviewAction> findByDocumentReviewIdAndReviewerId(UUID documentReviewId, UUID reviewerId);
}
