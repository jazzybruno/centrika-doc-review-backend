package rw.ac.rca.centrika.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne()
    @JoinColumn(name = "document_id")
    private Document document;
    @ManyToOne
    @JoinColumn(name = "user_id_requester")
    private User requester;
    @ManyToOne
    @JoinColumn(name = "user_id_approver")
    private User approver;
    @ManyToOne()
    @JoinColumn(name = "document_review_id")
    private DocumentReview documentReview;
}