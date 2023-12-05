package rw.ac.rca.centrika.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.rca.centrika.dtos.CreateReviewerDTO;
import rw.ac.rca.centrika.models.Reviewer;
import rw.ac.rca.centrika.services.ReviewerService;
import rw.ac.rca.centrika.utils.ApiResponse;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS})
@RequestMapping("/api/reviewers")
public class ReviewerController {

    private final ReviewerService reviewerService;

    @Autowired
    public ReviewerController(ReviewerService reviewerService) {
        this.reviewerService = reviewerService;
    }

    @GetMapping
    public ResponseEntity getAllReviewers() {
        List<Reviewer> reviewers = reviewerService.findAll();
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched all reviewers",
                reviewers
        ));
    }

    @GetMapping("/{reviewerId}")
    public ResponseEntity getReviewerById(@PathVariable UUID reviewerId) {
        Reviewer reviewer = reviewerService.findReviewerById(reviewerId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched reviewer by id",
                reviewer
        ));
    }

    @PostMapping("/create")
    public ResponseEntity createReviewer(@RequestBody CreateReviewerDTO createReviewerDTO) {
        Reviewer reviewer = reviewerService.createReviewer(createReviewerDTO);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully created reviewer",
                reviewer
        ));
    }

    @PutMapping("/update/{reviewerId}")
    public ResponseEntity updateReviewer(@PathVariable UUID reviewerId, @RequestBody CreateReviewerDTO updateReviewerDTO) {
        Reviewer reviewer = reviewerService.updateReviewer(reviewerId, updateReviewerDTO);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully updated reviewer",
                reviewer
        ));
    }

    @DeleteMapping("/delete/{reviewerId}")
    public ResponseEntity deleteReviewer(@PathVariable UUID reviewerId) {
        boolean isDeleted = reviewerService.deleteReviewer(reviewerId);
        if (isDeleted) {
            return ResponseEntity.ok().body(new ApiResponse(
                    true,
                    "Successfully deleted reviewer"
            ));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse(
                    false,
                    "Failed to delete reviewer"
            ));
        }
    }

    @GetMapping("/by-document-review/{documentReviewId}")
    public ResponseEntity getReviewersByDocumentReviewId(@PathVariable UUID documentReviewId) {
        List<Reviewer> reviewers = reviewerService.findByDocumentReviewId(documentReviewId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched reviewers by document review",
                reviewers
        ));
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity getReviewersByUserId(@PathVariable UUID userId) {
        List<Reviewer> reviewers = reviewerService.findByUserId(userId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched reviewers by user",
                reviewers
        ));
    }

    @GetMapping("/by-user-and-document-review/{userId}/{documentReviewId}")
    public ResponseEntity getReviewerByUserAndDocumentReview(@PathVariable UUID userId, @PathVariable UUID documentReviewId) {
        Reviewer reviewer = reviewerService.findByUserAndDocumentReview(documentReviewId, userId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched reviewer by user and document review",
                reviewer
        ));
    }

    // Add other endpoints as needed

}
