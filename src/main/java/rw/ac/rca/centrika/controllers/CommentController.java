package rw.ac.rca.centrika.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.rca.centrika.dtos.requests.CreateCommentDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateCommentDTO;
import rw.ac.rca.centrika.models.Comment;
import rw.ac.rca.centrika.services.CommentService;
import rw.ac.rca.centrika.utils.ApResponse;

import java.util.List;
import java.util.UUID;

@RestController
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
        return ResponseEntity.ok(ApResponse.success(comments));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity getCommentById(@PathVariable UUID commentId) {
        Comment comment = commentService.getCommentById(commentId);
        return ResponseEntity.ok(ApResponse.success(comment));
    }

    @PostMapping("/create")
    public ResponseEntity createComment(@RequestBody CreateCommentDTO createCommentDTO) {
        Comment comment = commentService.createComment(createCommentDTO);
        return ResponseEntity.ok(ApResponse.success(comment));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity updateComment(@PathVariable UUID commentId, @RequestBody UpdateCommentDTO updateCommentDTO) {
        Comment comment = commentService.updateComment(commentId, updateCommentDTO);
        return ResponseEntity.ok(ApResponse.success(comment));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable UUID commentId) {
        Comment comment = commentService.deleteComment(commentId);
        return ResponseEntity.ok(ApResponse.success(comment));
    }

    @GetMapping("/document-review/{documentReviewId}")
    public ResponseEntity getCommentByDocumentReview(@PathVariable UUID documentReviewId) {
        List<Comment> comments = commentService.getCommentByDocumentReview(documentReviewId);
        return ResponseEntity.ok(ApResponse.success(comments));
    }

    @GetMapping("/user")
    public ResponseEntity getCommentByUser() {
        List<Comment> comments = commentService.getCommentByUser();
        return ResponseEntity.ok(ApResponse.success(comments));
    }
}
