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
                "Successfully fetched the document reviews",
                documentReviews
                )
        );
    }

    @GetMapping("/reviewer/{reviewerId}")
    public ResponseEntity getAllDocumentReviewsByReviewer(@PathVariable UUID reviewerId) {
        List<DocumentReview> documentReviews = documentReviewService.getDocumentsReviewsThatWereRequested(reviewerId);
        return ResponseEntity.ok().body(new ApiResponse(
                        true,
                        "Successfully fetched the document reviews by the reviewer",
                        documentReviews
                )
        );
    }

    @GetMapping("/sender/{senderId}")
    public ResponseEntity getAllDocumentReviewsBySender(@PathVariable UUID senderId) {
        List<DocumentReview> documentReviews = documentReviewService.getDocumentsReviewsThatWereRequestedByUser(senderId);
        return ResponseEntity.ok().body(new ApiResponse(
                        true,
                        "Successfully fetched the document reviews by the sender",
                        documentReviews
                )
        );
    }
    @GetMapping("/{docReviewId}")
    public ResponseEntity getDocumentReviewById(@PathVariable UUID docReviewId) {
        DocumentReview documentReview = documentReviewService.getDocumentReviewById(docReviewId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully got a review",
                documentReview
        ));
    }
    @PostMapping("/request")
    public ResponseEntity requestDocumentReview(@RequestParam("file") MultipartFile docFile, @ModelAttribute() RequestReviewDTO requestReviewDTO) throws IOException {
        DocumentReview documentReview = documentReviewService.requestDocumentReview(docFile , requestReviewDTO);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully demanded a review",
                documentReview
        ));
    }

    @PutMapping("/{docReviewId}")
    public ResponseEntity updateDocumentReview(@PathVariable UUID docReviewId, @RequestBody UpdateDocumentReviewDTO updateDocumentReviewDTO) {
        DocumentReview documentReview = documentReviewService.updateDocumentReview(docReviewId, updateDocumentReviewDTO);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully updated a review",
                documentReview
        ));
    }

    @DeleteMapping("/{docReviewId}")
    public ResponseEntity deleteDocumentReview(@PathVariable UUID docReviewId) {
        DocumentReview documentReview = documentReviewService.deleteDocumentReview(docReviewId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully deleted a review",
                documentReview
        ));
    }
}
