package rw.ac.rca.centrika.dtos.requests;

import jakarta.persistence.*;
import rw.ac.rca.centrika.enumerations.ECategory;
import rw.ac.rca.centrika.enumerations.EDocStatus;
import rw.ac.rca.centrika.models.Department;
import rw.ac.rca.centrika.models.User;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CreateDocumentDTO {
    private String title;
    private String description;
    private String fileUrl;
    @Enumerated(EnumType.STRING)
    private ECategory category;
    @Enumerated(EnumType.STRING)
    private EDocStatus status;
    private int referenceNumber;
    private UUID departmentId;
}
