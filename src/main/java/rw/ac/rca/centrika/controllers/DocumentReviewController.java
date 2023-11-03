package rw.ac.rca.centrika.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rw.ac.rca.centrika.dtos.requests.CreateDocumentReviewDTO;
import rw.ac.rca.centrika.dtos.requests.RequestReviewDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateDocumentReviewDTO;
import rw.ac.rca.centrika.models.DocumentReview;
import rw.ac.rca.centrika.services.DocumentReviewService;
import rw.ac.rca.centrika.utils.ApResponse;

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
        return ResponseEntity.ok(ApResponse.success(documentReviews));
    }

    @GetMapping("/{docReviewId}")
    public ResponseEntity getDocumentReviewById(@PathVariable UUID docReviewId) {
        DocumentReview documentReview = documentReviewService.getDocumentReviewById(docReviewId);
        return ResponseEntity.ok(ApResponse.success(documentReview));
    }
    @PostMapping("/request")
    public ResponseEntity requestDocumentReview(@RequestParam("file") MultipartFile docFile, @ModelAttribute() RequestReviewDTO requestReviewDTO) throws IOException {
        DocumentReview documentReview = documentReviewService.requestDocumentReview(docFile , requestReviewDTO);
        return ResponseEntity.ok(ApResponse.success(documentReview));
    }

    @PutMapping("/{docReviewId}")
    public ResponseEntity updateDocumentReview(@PathVariable UUID docReviewId, @RequestBody UpdateDocumentReviewDTO updateDocumentReviewDTO) {
        DocumentReview documentReview = documentReviewService.updateDocumentReview(docReviewId, updateDocumentReviewDTO);
        return ResponseEntity.ok(ApResponse.success(documentReview));
    }

    @DeleteMapping("/{docReviewId}")
    public ResponseEntity deleteDocumentReview(@PathVariable UUID docReviewId) {
        DocumentReview documentReview = documentReviewService.deleteDocumentReview(docReviewId);
        return ResponseEntity.ok(ApResponse.success(documentReview));
    }
}
