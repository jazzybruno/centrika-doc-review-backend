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
public class CreateDeliberationDTO {

    private UUID studentId;

    private String academicYear;

    private Double totalScore;

    private Double totalPassMark;

    private Double totalOutOf;

    private Integer position;

    @Enumerated (EnumType.STRING)
    private EDecision decision;

    @Override
    public String toString() {
        return "{" +
                "studentId:" + studentId +
                ", academicYear:'" + academicYear + '\'' +
                ", totalScore:" + totalScore +
                ", totalPassMark:" + totalPassMark +
                ", totalOutOf:" + totalOutOf +
                ", position:" + position +
                ", decision:" + decision +
                '}';
    }
}
