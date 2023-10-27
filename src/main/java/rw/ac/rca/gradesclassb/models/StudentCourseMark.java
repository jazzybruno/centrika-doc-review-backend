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
public class StudentCourseMark {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Course course;

    private Double score;//65

    private Double passMark;//70

    private Double outOf;//100

    @Enumerated(EnumType.STRING)
    private EDecision decision;
    private String academicYear;
    private LocalDate date;
}
