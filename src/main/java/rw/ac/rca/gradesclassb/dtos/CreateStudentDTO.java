package rw.ac.rca.gradesclassb.dtos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.ac.rca.gradesclassb.enumerations.EGender;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudentDTO {

    private String firstName;

    private String lastName;

    @Enumerated (EnumType.STRING)
    private EGender gender;

    @Override
    public String toString() {
        return "{" +
                "firstName:'" + firstName + '\'' +
                ", lastName:'" + lastName + '\'' +
                ", gender:" + gender +
                '}';
    }
}
