package rw.ac.rca.gradesclassb.dtos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.ac.rca.gradesclassb.enumerations.EDecision;

import java.util.UUID;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudentCourseMarkDTO {

    private UUID studentId;

    private UUID courseId;

    private Double score;

    private Double passMark;

    private Double outOf;

    private String academicYear;

    @Enumerated (EnumType.STRING)
    private EDecision decision;
}
