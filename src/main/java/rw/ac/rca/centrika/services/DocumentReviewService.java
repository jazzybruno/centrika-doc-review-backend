package rw.ac.rca.centrika.services;

import org.springframework.web.multipart.MultipartFile;
import rw.ac.rca.centrika.dtos.requests.CreateDocumentReviewDTO;
import rw.ac.rca.centrika.dtos.requests.RequestReviewDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateDocumentReviewDTO;
import rw.ac.rca.centrika.models.DocumentReview;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface DocumentReviewService {
    // CRUD methods
    public List<DocumentReview> getAllDocumentReviews();
    public DocumentReview getDocumentReviewById(UUID docReviewId);

    DocumentReview requestDocumentReview(MultipartFile file,  RequestReviewDTO requestReviewDTO) throws IOException;

    public DocumentReview updateDocumentReview(UUID docReviewId , UpdateDocumentReviewDTO updateDocumentReviewDTO) ;
    public DocumentReview deleteDocumentReview(UUID docReviewId);
    // other methods
}
