package rw.ac.rca.centrika.dtos.requests;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import rw.ac.rca.centrika.enumerations.EDocStatus;

import java.util.UUID;

public class UpdateDocumentDTO {
    private String title;
    private String description;
    private String fileUrl;
    @Enumerated(EnumType.STRING)
    private EDocStatus status;
    private int referenceNumber;
    private UUID departmentId;
}
