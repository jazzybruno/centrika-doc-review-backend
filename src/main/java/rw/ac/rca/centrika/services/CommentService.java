package rw.ac.rca.centrika.services;

import rw.ac.rca.centrika.dtos.requests.CreateCommentDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateCommentDTO;
import rw.ac.rca.centrika.models.Comment;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    // CRUD methods
    public List<Comment> getAllComments();
    public Comment getCommentById(UUID commentId);
    public Comment createComment(CreateCommentDTO createCommentDTO);
    public Comment updateComment(UUID commentId , UpdateCommentDTO updateCommentDTO);
    public Comment deleteComment(UUID commentId);
    // other methods
    public List<Comment> getCommentByDocumentReview(UUID documentReviewId);
    public List<Comment> getCommentByUser();

}