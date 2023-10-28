package rw.ac.rca.centrika.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String content;
    @Column(name = "created_at")
    private Date createdAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User commentCreator;
    @ManyToOne
    @JoinColumn(name = "documentReview_id")
    private DocumentReview documentReview;

    public Comment(String content, Date createdAt, User commentCreator, DocumentReview documentReview) {
        this.content = content;
        this.createdAt = createdAt;
        this.commentCreator = commentCreator;
        this.documentReview = documentReview;
    }
}
