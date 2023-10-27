package rw.ac.rca.gradesclassb.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.ac.rca.gradesclassb.enumerations.EStatus;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private Double passMark;//70

    private Double outOf;//100

    @Enumerated(EnumType.STRING)
    private EStatus status;

    @Column(unique = true)
    private String code;
}
