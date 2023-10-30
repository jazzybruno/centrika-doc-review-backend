package rw.ac.rca.centrika.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID notId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String message;
    private boolean isRead;

    public Notification(User user, String message, boolean isRead) {
        this.user = user;
        this.message = message;
        this.isRead = isRead;
    }
}
