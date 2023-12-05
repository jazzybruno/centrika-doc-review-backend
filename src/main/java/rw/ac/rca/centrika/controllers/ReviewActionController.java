package rw.ac.rca.centrika.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.rca.centrika.dtos.CreateReviewActionDTO;
import rw.ac.rca.centrika.dtos.UpdateReviewActionDTO;
import rw.ac.rca.centrika.enumerations.EReviewStatus;
import rw.ac.rca.centrika.models.ReviewAction;
import rw.ac.rca.centrika.services.ReviewActionService;
import rw.ac.rca.centrika.utils.ApiResponse;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS})
@RequestMapping("/api/review-actions")
public class ReviewActionController {

    private final ReviewActionService reviewActionService;

    @Autowired
    public ReviewActionController(ReviewActionService reviewActionService) {
        this.reviewActionService = reviewActionService;
    }

    @GetMapping
    public ResponseEntity getAllReviewActions() {
        List<ReviewAction> reviewActions = reviewActionService.findAll();
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched all review actions",
                reviewActions
        ));
    }

    @GetMapping("/{reviewActionId}")
    public ResponseEntity getReviewActionById(@PathVariable UUID reviewActionId) {
        ReviewAction reviewAction = reviewActionService.findById(reviewActionId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched review action by id",
                reviewAction
        ));
    }

    @PostMapping("/create")
    public ResponseEntity createReviewAction(@RequestBody CreateReviewActionDTO reviewActionDTO) {
        ReviewAction reviewAction = reviewActionService.save(reviewActionDTO);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully created review action",
                reviewAction
        ));
    }

    @PutMapping("/update/{reviewActionId}")
    public ResponseEntity updateReviewAction(@PathVariable UUID reviewActionId, @RequestBody UpdateReviewActionDTO updateReviewActionDTO) {
        ReviewAction reviewAction = reviewActionService.update(reviewActionId, updateReviewActionDTO);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully updated review action",
                reviewAction
        ));
    }

    @DeleteMapping("/{reviewActionId}")
    public ResponseEntity deleteReviewAction(@PathVariable UUID reviewActionId) {
        reviewActionService.delete(reviewActionId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully deleted review action"
        ));
    }

    @GetMapping("/by-reviewer/{reviewerId}")
    public ResponseEntity getReviewActionsByReviewerId(@PathVariable UUID reviewerId) {
        List<ReviewAction> reviewActions = reviewActionService.findByReviewerId(reviewerId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched review actions by reviewer",
                reviewActions
        ));
    }

    @GetMapping("/by-document/{documentId}")
    public ResponseEntity getReviewActionsByDocumentId(@PathVariable UUID documentId) {
        List<ReviewAction> reviewActions = reviewActionService.findByDocumentId(documentId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched review actions by document",
                reviewActions
        ));
    }

    @GetMapping("/by-reviewer-and-document/{reviewerId}/{documentId}")
    public ResponseEntity getReviewActionsByReviewerAndDocumentId(
            @PathVariable UUID reviewerId,
            @PathVariable UUID documentId) {
        List<ReviewAction> reviewActions = reviewActionService.findByReviewerIdAndDocumentId(reviewerId, documentId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched review actions by reviewer and document",
                reviewActions
        ));
    }

    @GetMapping("/by-reviewer-and-document-and-status/{reviewerId}/{documentId}/{status}")
    public ResponseEntity getReviewActionsByReviewerAndDocumentIdAndStatus(
            @PathVariable UUID reviewerId,
            @PathVariable UUID documentId,
            @PathVariable EReviewStatus status) {
        List<ReviewAction> reviewActions = reviewActionService.findByReviewerIdAndDocumentIdAndStatus(reviewerId, documentId, status);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched review actions by reviewer, document, and status",
                reviewActions
        ));
    }

    @GetMapping("/by-reviewer-and-status/{reviewerId}/{status}")
    public ResponseEntity getReviewActionsByReviewerAndStatus(
            @PathVariable UUID reviewerId,
            @PathVariable EReviewStatus status) {
        List<ReviewAction> reviewActions = reviewActionService.findByReviewerIdAndStatus(reviewerId, status);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched review actions by reviewer and status",
                reviewActions
        ));
    }

    @GetMapping("/by-document-review/{documentReviewId}")
    public ResponseEntity getReviewActionsByDocumentReviewId(@PathVariable UUID documentReviewId) {
        List<ReviewAction> reviewActions = reviewActionService.findByDocumentReviewId(documentReviewId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched review actions by document review",
                reviewActions
        ));
    }

    @GetMapping("/by-document-review-and-status/{documentReviewId}/{status}")
    public ResponseEntity getReviewActionsByDocumentReviewIdAndStatus(
            @PathVariable UUID documentReviewId,
            @PathVariable EReviewStatus status) {
        List<ReviewAction> reviewActions = reviewActionService.findByDocumentReviewIdAndStatus(documentReviewId, status);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched review actions by document review and status",
                reviewActions
        ));
    }

    @GetMapping("/by-document-review-and-reviewer/{documentReviewId}/{reviewerId}")
    public ResponseEntity getReviewActionsByDocumentReviewIdAndReviewerId(
            @PathVariable UUID documentReviewId,
            @PathVariable UUID reviewerId) {
        List<ReviewAction> reviewActions = reviewActionService.findByDocumentReviewIdAndReviewerId(documentReviewId, reviewerId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched review actions by document review and reviewer",
                reviewActions
        ));
    }

    // Add other endpoints as needed

}
