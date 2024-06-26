package rw.ac.rca.centrika.services;

import org.springframework.web.multipart.MultipartFile;
import rw.ac.rca.centrika.dtos.SetDeadlineDTO;
import rw.ac.rca.centrika.dtos.requests.*;
import rw.ac.rca.centrika.exceptions.ResourceNotFoundException;
import rw.ac.rca.centrika.models.DocumentReview;

import javax.print.Doc;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface DocumentReviewService {
    // CRUD methods
    public List<DocumentReview> getAllDocumentReviews();
    public DocumentReview getDocumentReviewById(UUID docReviewId);
    DocumentReview requestDocumentReview(RequestReviewDTO requestReviewDTO) throws IOException;
    DocumentReview changeWhoHasFinalReview(UUID docReviewId , UUID newFinalReviewerId) throws ResourceNotFoundException;
    boolean remindReviewer(UUID reviewerId);
    public DocumentReview updateDocumentReview(UUID docReviewId , UpdateDocumentReviewDTO updateDocumentReviewDTO) ;
    public DocumentReview deleteDocumentReview(UUID docReviewId);
    // other methods
    public List<DocumentReview> getDocumentReviewByCreator(UUID creatorId);
    public DocumentReview forwardTheDocument(ForwardDocumentDTO forwardDocumentDTO);
    List<DocumentReview> findDocumentReviewByDocumentId(UUID documentId);
    List<DocumentReview> getDocumentReviewBySenderDepartment(UUID senderDepartmentId);
    List<DocumentReview> getDocumentReviewByReceiverDepartment(UUID receiverDepartmentId);
    DocumentReview setDeadlineForReview(UUID documentReviewId , SetDeadlineDTO deadline);
}
