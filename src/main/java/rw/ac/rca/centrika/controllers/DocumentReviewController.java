package rw.ac.rca.centrika.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.rca.centrika.dtos.requests.CreateDocumentReviewDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateDocumentReviewDTO;
import rw.ac.rca.centrika.models.DocumentReview;
import rw.ac.rca.centrika.services.DocumentReviewService;
import rw.ac.rca.centrika.utils.ApiResponse;

import java.util.List;
import java.util.UUID;

@RestController
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
        return ResponseEntity.ok(ApiResponse.success(documentReviews));
    }

    @GetMapping("/{docReviewId}")
    public ResponseEntity getDocumentReviewById(@PathVariable UUID docReviewId) {
        DocumentReview documentReview = documentReviewService.getDocumentReviewById(docReviewId);
        return ResponseEntity.ok(ApiResponse.success(documentReview));
    }

    @PostMapping("/request")
    public ResponseEntity requestDocumentReview(@RequestBody CreateDocumentReviewDTO createDocumentReviewDTO) {
        DocumentReview documentReview = documentReviewService.requestDocumentReview(createDocumentReviewDTO);
        return ResponseEntity.ok(ApiResponse.success(documentReview));
    }

    @PutMapping("/{docReviewId}")
    public ResponseEntity updateDocumentReview(@PathVariable UUID docReviewId, @RequestBody UpdateDocumentReviewDTO updateDocumentReviewDTO) {
        DocumentReview documentReview = documentReviewService.updateDocumentReview(docReviewId, updateDocumentReviewDTO);
        return ResponseEntity.ok(ApiResponse.success(documentReview));
    }

    @DeleteMapping("/{docReviewId}")
    public ResponseEntity deleteDocumentReview(@PathVariable UUID docReviewId) {
        DocumentReview documentReview = documentReviewService.deleteDocumentReview(docReviewId);
        return ResponseEntity.ok(ApiResponse.success(documentReview));
    }
}
