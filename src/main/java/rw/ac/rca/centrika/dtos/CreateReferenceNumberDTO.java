package rw.ac.rca.centrika.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.ac.rca.centrika.enumerations.ERefNumStatus;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateReferenceNumberDTO {
    private String title;
    private String destination;
    private ERefNumStatus status;
    private UUID createdBy;

}
