package rw.ac.rca.centrika.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.ac.rca.centrika.enumerations.EDocStatus;

import javax.print.Doc;
import java.util.*;

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

    @Column(name = "updated_at")
    private Date updatedAt = null;
    @Enumerated(EnumType.STRING)
    private EDocStatus  status;
    // relations
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_reviews" , joinColumns = @JoinColumn(name = "document_id") , inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> reviewers =  new HashSet<User>();

    @OneToMany(mappedBy = "reviewDoc" , fetch = FetchType.EAGER)
    private List<Document> reviewDocList = new ArrayList<Document>();

    @Column(name = "user_id")
    private UUID creator;

    @Transient
    private User creatorUser;

    @Column(name = "current_doc_id")
    private UUID currentDocument;
    @OneToMany(mappedBy = "documentReview" , fetch = FetchType.LAZY)
    private Set<Comment> comments = new HashSet<>();
    public DocumentReview(Date createdAt, EDocStatus status, Set<User> reviewers , UUID creator ) {
        this.createdAt = createdAt;
        this.status = status;
        this.reviewers = reviewers;
        this.creator = creator;
    }
}
