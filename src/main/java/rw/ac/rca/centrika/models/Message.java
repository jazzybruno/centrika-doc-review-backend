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
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String content;
    @ManyToOne
    private User sender;
    @ManyToOne
    private User receiver;
    @ManyToOne
    @JoinTable(name = "user_groups" , joinColumns = @JoinColumn(name = "group_id") , inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Group group;
}