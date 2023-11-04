package rw.ac.rca.centrika.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
    @JsonIgnore
    @OneToMany(mappedBy = "documentReview" , fetch = FetchType.LAZY)
    private Set<Comment> comments = new HashSet<>();
    public DocumentReview(Date createdAt, EDocStatus status, Set<User> reviewers, Document reviewDoc) {
        this.createdAt = createdAt;
        this.status = status;
        this.reviewers = reviewers;
        this.reviewDoc = reviewDoc;
    }
}
