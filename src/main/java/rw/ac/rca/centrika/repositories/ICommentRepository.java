package rw.ac.rca.centrika.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.rca.centrika.models.Comment;
import rw.ac.rca.centrika.models.Document;

import java.util.UUID;

public interface ICommentRepository extends JpaRepository<Comment, UUID> {
    interface IDocumentRepository extends JpaRepository<Document, UUID> {
    }
}