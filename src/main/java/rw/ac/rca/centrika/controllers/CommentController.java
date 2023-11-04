package rw.ac.rca.centrika.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.rca.centrika.dtos.requests.CreateCommentDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateCommentDTO;
import rw.ac.rca.centrika.models.Comment;
import rw.ac.rca.centrika.services.CommentService;
import rw.ac.rca.centrika.utils.ApResponse;
import rw.ac.rca.centrika.utils.ApiResponse;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS})
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched all the comments ",
                comments
        ));
    }

    @GetMapping("/document-review/{documentReviewId}")
    public ResponseEntity getCommentByDocumentReview(@PathVariable UUID documentReviewId) {
        List<Comment> comments = commentService.getCommentByDocumentReview(documentReviewId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched all the comments by review ",
                comments
        ));
    }

    @GetMapping("/user")
    public ResponseEntity getCommentByUser() {
        List<Comment> comments = commentService.getCommentByUser();
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched all the comments by user ",
                comments
        ));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity getCommentById(@PathVariable UUID commentId) {
        Comment comment = commentService.getCommentById(commentId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched  the comment",
                comment
        ));
    }

    @PostMapping("/create")
    public ResponseEntity createComment(@RequestBody CreateCommentDTO createCommentDTO) {
        Comment comment = commentService.createComment(createCommentDTO);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully created the comment",
                comment
        ));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity updateComment(@PathVariable UUID commentId, @RequestBody UpdateCommentDTO updateCommentDTO) {
        Comment comment = commentService.updateComment(commentId, updateCommentDTO);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully updated the comment ",
                comment
        ));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable UUID commentId) {
        Comment comment = commentService.deleteComment(commentId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully deleted the comment ",
                comment
        ));
    }

}
