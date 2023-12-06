package rw.ac.rca.centrika.services;

import rw.ac.rca.centrika.dtos.CreateReferenceNumberDTO;
import rw.ac.rca.centrika.enumerations.ERefNumStatus;
import rw.ac.rca.centrika.models.ReferenceNumber;

import java.util.List;
import java.util.UUID;

public interface ReferenceNumberService {
    // CRUD Methods - Create, Read, Update, Delete
    public List<ReferenceNumber> getAllReferenceNumbers();
    public ReferenceNumber getReferenceNumberById(UUID referenceNumberId);
    public ReferenceNumber createReferenceNumber(CreateReferenceNumberDTO referenceNumberDTO);
    public ReferenceNumber deleteReferenceNumber(UUID referenceNumberId);

    // Custom Methods
    public ReferenceNumber updateReferenceNumberStatus(UUID referenceNumberId, ERefNumStatus status);
    public ReferenceNumber updateReferenceNumberDestination(UUID referenceNumberId, String destination);
    public List<ReferenceNumber> getReferenceNumbersByDepartment(UUID departmentId);

    // Search Methods
    public List<ReferenceNumber> searchReferenceNumberByDestination(String destination);
    public List<ReferenceNumber> searchReferenceNumberByStatus(ERefNumStatus status);

    List<ReferenceNumber> getReferenceNumbersByUser(UUID userId);
}
