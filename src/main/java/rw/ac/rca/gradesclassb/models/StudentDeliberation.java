package rw.ac.rca.gradesclassb.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.ac.rca.gradesclassb.enumerations.EDecision;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class StudentDeliberation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Student student;

    private String academicYear;

    private Double totalScore;

    private Double totalPassMark;

    private Double totalOutOf;

    private Integer position;

    @Enumerated(EnumType.STRING)
    private EDecision decision;

    private LocalDate date;
}
