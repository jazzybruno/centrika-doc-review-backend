package rw.ac.rca.centrika.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;

    @ManyToOne
    @JoinColumn(name = "sending_department_id")
    private Department sendingDepartment;
    @ManyToOne
    @JoinColumn(name = "receiving_department_id")
    private Department recevingDepartment;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name = "expected_complete_time")
    private Date expectedCompleteTime;

    @Column(name = "deadline")
    private Date deadline;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt = null;

    @ManyToMany(mappedBy = "documentReview")
    private Set<Reviewer> reviewers = new HashSet<>();

    // constructor without id
    public DocumentReview(Document document, Department sendingDepartment, Department recevingDepartment, User createdBy, Date expectedCompleteTime, Date deadline, Date createdAt, Date updatedAt) {
        this.document = document;
        this.sendingDepartment = sendingDepartment;
        this.recevingDepartment = recevingDepartment;
        this.createdBy = createdBy;
        this.expectedCompleteTime = expectedCompleteTime;
        this.deadline = deadline;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
