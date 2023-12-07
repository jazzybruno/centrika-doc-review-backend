package rw.ac.rca.centrika.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.rca.centrika.models.*;

import java.util.List;
import java.util.UUID;

public interface ICommentRepository extends JpaRepository<Comment, UUID> {
    public List<Comment > findAllByCommentCreator(User user);
    public Comment findAllByReviewAction(ReviewAction reviewAction);

}