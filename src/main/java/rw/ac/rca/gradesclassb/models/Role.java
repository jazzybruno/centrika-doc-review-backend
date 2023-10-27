package rw.ac.rca.gradesclassb.models;

import jakarta.persistence.*;
import lombok.*;
import rw.ac.rca.gradesclassb.enumerations.EUserRole;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    @Column (name = "role_id")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private EUserRole roleName;
}
