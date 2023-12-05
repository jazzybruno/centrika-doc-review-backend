package rw.ac.rca.centrika.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.ac.rca.centrika.enumerations.EDocStatus;
import rw.ac.rca.centrika.enumerations.EReviewStatus;

import java.util.Date;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewAction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    private Reviewer reviewer;

    @ManyToOne
    @JoinColumn(name = "document_review_id")
    private DocumentReview documentReview;

    @Enumerated(EnumType.STRING)
    private EReviewStatus action;

    private Date createdAt;

    private Date updatedAt;

    public ReviewAction(Reviewer reviewer, DocumentReview documentReview, EReviewStatus action, Date createdAt, Date updatedAt) {
        this.reviewer = reviewer;
        this.documentReview = documentReview;
        this.action = action;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
