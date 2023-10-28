package rw.ac.rca.centrika.services.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.rca.centrika.dtos.requests.CreateCommentDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateCommentDTO;
import rw.ac.rca.centrika.exceptions.InternalServerErrorException;
import rw.ac.rca.centrika.exceptions.NotFoundException;
import rw.ac.rca.centrika.exceptions.UnAuthorizedException;
import rw.ac.rca.centrika.models.Comment;
import rw.ac.rca.centrika.models.DocumentReview;
import rw.ac.rca.centrika.models.User;
import rw.ac.rca.centrika.repositories.ICommentRepository;
import rw.ac.rca.centrika.repositories.IDocumentReviewRepository;
import rw.ac.rca.centrika.security.UserPrincipal;
import rw.ac.rca.centrika.services.CommentService;
import rw.ac.rca.centrika.utils.UserUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final ICommentRepository commentRepository;
    private final UserServiceImpl userService;
    private  final IDocumentReviewRepository documentReviewRepository;
    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Comment getCommentById(UUID commentId) {
        return commentRepository.findById(commentId).orElseThrow(()-> {throw new NotFoundException("The comment was not found");
        });
    }

    @Override
    public Comment createComment(CreateCommentDTO createCommentDTO) {
        DocumentReview documentReview = documentReviewRepository.findById(createCommentDTO.getDocumentReviewId()).orElseThrow(() -> {throw new NotFoundException("The document review was not found");});
        if(UserUtils.isUserLoggedIn()){
          try {
              UserPrincipal userPrincipal = UserUtils.getLoggedInUser();
              assert userPrincipal != null;
              User user = userService.getUserById(userPrincipal.getId());
              Date createdAt = new Date();
              Comment comment = new Comment(
                      createCommentDTO.getContent(),
                      createdAt,
                      user,
                      documentReview
              );
              commentRepository.save(comment);
              return comment;
          }catch (Exception e){
              throw new InternalServerErrorException(e.getMessage());
          }
        }else{
            throw new UnAuthorizedException("You are not authorized ");
        }
    }

    @Override
    @Transactional
    public Comment updateComment(UUID commentId, UpdateCommentDTO updateCommentDTO) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> {throw new NotFoundException("The comment was not found");
        });
        try {
            comment.setContent(updateCommentDTO.getContent());
            return comment;
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public Comment deleteComment(UUID commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> {throw new NotFoundException("The comment was not found");
        });
        try {
            commentRepository.deleteById(commentId);
            return comment;
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<Comment> getCommentByDocumentReview(UUID documentReviewId) {
        DocumentReview documentReview = documentReviewRepository.findById(documentReviewId).orElseThrow(() -> {throw new NotFoundException("The document review was not found");});
        try {
            return commentRepository.findAllByDocumentReview(documentReview);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<Comment> getCommentByUser() {
     if(UserUtils.isUserLoggedIn()){
         UserPrincipal userPrincipal = UserUtils.getLoggedInUser();
         assert userPrincipal != null;
         User user = userService.getUserById(userPrincipal.getId());
         try {
             return commentRepository.findAllByCommentCreator(user);
         }catch (Exception e){
             throw new InternalServerErrorException(e.getMessage());
         }
     }else{
         throw new UnAuthorizedException("You are not authorized ");
     }
    }
}
