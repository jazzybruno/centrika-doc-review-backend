package rw.ac.rca.centrika.services;

import rw.ac.rca.centrika.dtos.requests.CreateDocumentReviewDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateDocumentReviewDTO;
import rw.ac.rca.centrika.models.DocumentReview;

import java.util.List;
import java.util.UUID;

public interface DocumentReviewService {
    // CRUD methods
    public List<DocumentReview> getAllDocumentReviews();
    public DocumentReview getDocumentReviewById(UUID docReviewId);
    public DocumentReview requestDocumentReview(CreateDocumentReviewDTO  createDocumentReviewDTO);
    public DocumentReview updateDocumentReview(UUID docReviewId , UpdateDocumentReviewDTO updateDocumentReviewDTO) ;
    public DocumentReview deleteDocumentReview(UUID docReviewId);
    // other methods
}
