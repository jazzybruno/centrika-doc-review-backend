package rw.ac.rca.centrika.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.ac.rca.centrika.enumerations.EDocStatus;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DocumentReview {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "created_at")
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private EDocStatus  status;
    // relations
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_reviews" , joinColumns = @JoinColumn(name = "document_id") , inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> reviewers =  new HashSet<User>();
    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document reviewDoc;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "comments_review" , joinColumns = @JoinColumn(name = "document_id") , inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private Set<Comment> comments = new HashSet<>();
}
