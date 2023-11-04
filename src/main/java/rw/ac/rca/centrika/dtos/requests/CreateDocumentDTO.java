package rw.ac.rca.centrika.dtos.requests;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.ac.rca.centrika.enumerations.ECategory;
import rw.ac.rca.centrika.enumerations.EDocStatus;
import rw.ac.rca.centrika.models.Department;
import rw.ac.rca.centrika.models.User;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateDocumentDTO {
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private ECategory category;
    private UUID departmentId;
    private UUID creator;

}
