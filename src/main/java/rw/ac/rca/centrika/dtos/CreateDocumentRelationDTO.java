package rw.ac.rca.centrika.dtos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.ac.rca.centrika.enumerations.ERelationType;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateDocumentRelationDTO {
    private UUID parentDocumentId;
    private UUID childDocumentId;
    @Enumerated(EnumType.STRING)
    private ERelationType relationType;
}
