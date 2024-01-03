package rw.ac.rca.centrika.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.rca.centrika.dtos.SetDeadlineDTO;
import rw.ac.rca.centrika.dtos.requests.ForwardDocumentDTO;
import rw.ac.rca.centrika.dtos.requests.RequestReviewDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateDocumentReviewDTO;
import rw.ac.rca.centrika.models.DocumentReview;
import rw.ac.rca.centrika.services.DocumentReviewService;
import rw.ac.rca.centrika.utils.ApiResponse;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS})
@RequestMapping("/api/document-reviews")
public class DocumentReviewController {

    private final DocumentReviewService documentReviewService;

    @Autowired
    public DocumentReviewController(DocumentReviewService documentReviewService) {
        this.documentReviewService = documentReviewService;
    }

    @GetMapping
    public ResponseEntity getAllDocumentReviews() {
        List<DocumentReview> documentReviews = documentReviewService.getAllDocumentReviews();
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched all document reviews",
                documentReviews
        ));
    }

    @GetMapping("/{docReviewId}")
    public ResponseEntity getDocumentReviewById(@PathVariable UUID docReviewId) {
        DocumentReview documentReview = documentReviewService.getDocumentReviewById(docReviewId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched document review by id",
                documentReview
        ));
    }

    @PostMapping("/request")
    public ResponseEntity requestDocumentReview(@RequestBody RequestReviewDTO requestReviewDTO) {
        try {
            DocumentReview documentReview = documentReviewService.requestDocumentReview(requestReviewDTO);
            return ResponseEntity.ok().body(new ApiResponse(
                    true,
                    "Successfully requested document review",
                    documentReview
            ));
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(
                    false,
                    "Error while processing the request: " + e.getMessage(),
                    null
            ));
        }
    }

    @PostMapping("/remind/{reviewerId}")
    public ResponseEntity remindReviewer(@PathVariable UUID reviewerId) {
        boolean isReminded = documentReviewService.remindReviewer(reviewerId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully reminded reviewer",
                isReminded
        ));
    }

    @PutMapping("/{docReviewId}")
    public ResponseEntity updateDocumentReview(@PathVariable UUID docReviewId, @RequestBody UpdateDocumentReviewDTO updateDocumentReviewDTO) {
        DocumentReview documentReview = documentReviewService.updateDocumentReview(docReviewId, updateDocumentReviewDTO);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully updated document review",
                documentReview
        ));
    }

    @DeleteMapping("/{docReviewId}")
    public ResponseEntity deleteDocumentReview(@PathVariable UUID docReviewId) {
        DocumentReview documentReview = documentReviewService.deleteDocumentReview(docReviewId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully deleted document review",
                documentReview
        ));
    }

    @GetMapping("/by-creator/{creatorId}")
    public ResponseEntity getDocumentReviewByCreator(@PathVariable UUID creatorId) {
        List<DocumentReview> documentReviews = documentReviewService.getDocumentReviewByCreator(creatorId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched document reviews by creator",
                documentReviews
        ));
    }

    @PostMapping("/forward")
    public ResponseEntity forwardTheDocument(@RequestBody ForwardDocumentDTO forwardDocumentDTO) {
        DocumentReview documentReview = documentReviewService.forwardTheDocument(forwardDocumentDTO);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully forwarded the document for review",
                documentReview
        ));
    }

    @GetMapping("/by-document/{documentId}")
    public ResponseEntity findDocumentReviewByDocumentId(@PathVariable UUID documentId) {
        List<DocumentReview> documentReviews = documentReviewService.findDocumentReviewByDocumentId(documentId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched document reviews by document",
                documentReviews
        ));
    }

    @GetMapping("/by-sender-department/{senderDepartmentId}")
    public ResponseEntity getDocumentReviewBySenderDepartment(@PathVariable UUID senderDepartmentId) {
        List<DocumentReview> documentReviews = documentReviewService.getDocumentReviewBySenderDepartment(senderDepartmentId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched document reviews by sender department",
                documentReviews
        ));
    }

    @GetMapping("/by-receiver-department/{receiverDepartmentId}")
    public ResponseEntity getDocumentReviewByReceiverDepartment(@PathVariable UUID receiverDepartmentId) {
        List<DocumentReview> documentReviews = documentReviewService.getDocumentReviewByReceiverDepartment(receiverDepartmentId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched document reviews by receiver department",
                documentReviews
        ));
    }

    @PatchMapping("/deadline/{documentReviewId}")
    public ResponseEntity setDeadlineForReview(@PathVariable UUID documentReviewId , @RequestBody SetDeadlineDTO deadline) {
        DocumentReview documentReview = documentReviewService.setDeadlineForReview(documentReviewId , deadline);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully set deadline for review",
                documentReview
        ));
    }
}
