package rw.ac.rca.centrika.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @OneToOne
    @JoinColumn(name = "department_head")
    private User departmentHead = null;

    public Department(String name, String description, User createdBy) {
        this.name = name;
        this.description = description;
        this.createdBy = createdBy;
    }
}
