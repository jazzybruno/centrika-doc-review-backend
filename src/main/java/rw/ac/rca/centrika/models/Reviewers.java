package rw.ac.rca.centrika.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reviewers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    private User user;
    private Date createdAt;
    private Date updatedAt;

    public Reviewers(User user, Date createdAt, Date updatedAt) {
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
