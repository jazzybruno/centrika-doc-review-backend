package rw.ac.rca.centrika.services;

import org.springframework.web.multipart.MultipartFile;
import rw.ac.rca.centrika.dtos.requests.*;
import rw.ac.rca.centrika.models.DocumentReview;

import javax.print.Doc;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface DocumentReviewService {
    // CRUD methods
    public List<DocumentReview> getAllDocumentReviews();
    public DocumentReview getDocumentReviewById(UUID docReviewId);

    DocumentReview requestDocumentReview(MultipartFile file,  RequestReviewDTO requestReviewDTO) throws IOException;

    public DocumentReview updateDocumentReview(MultipartFile file , UUID docReviewId , UpdateDocumentReviewDTO updateDocumentReviewDTO) ;
    public DocumentReview deleteDocumentReview(UUID docReviewId);
    // other methods

    public List<DocumentReview> getDocumentsReviewsThatWereRequested(UUID reviewerId);
    public List<DocumentReview> getDocumentsReviewsThatWereRequestedByUser(UUID senderId);
    public DocumentReview reviewTheDocument(ReviewDocumentDTO reviewDocumentDTO);
    public DocumentReview forwardTheDocument(ForwardDocumentDTO forwardDocumentDTO);

    List<DocumentReview> getDocumentReviewByDepartment(UUID departmentId);
}
