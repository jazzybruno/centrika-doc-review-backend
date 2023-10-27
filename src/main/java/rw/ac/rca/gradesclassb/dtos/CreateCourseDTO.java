package rw.ac.rca.gradesclassb.dtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCourseDTO {

    private String name;

    private Double passMark;//70

    private Double outOf;//100

    @Column (unique = true)
    private String code;
}
