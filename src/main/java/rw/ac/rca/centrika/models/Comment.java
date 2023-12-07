package rw.ac.rca.centrika.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User commentCreator;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "review_action_id")
    private ReviewAction reviewAction;

    public Comment(String content, Date createdAt, User commentCreator, ReviewAction reviewAction) {
        this.content = content;
        this.createdAt = createdAt;
        this.commentCreator = commentCreator;
        this.reviewAction = reviewAction;
    }
}
