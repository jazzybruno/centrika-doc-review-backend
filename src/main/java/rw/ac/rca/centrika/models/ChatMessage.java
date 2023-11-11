package rw.ac.rca.centrika.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID messageId;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;
    @Column
    private String message;
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;
}
