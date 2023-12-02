package rw.ac.rca.centrika.services.serviceImpl;

import rw.ac.rca.centrika.dtos.CreateReferenceNumberDTO;
import rw.ac.rca.centrika.enumerations.ERefNumStatus;
import rw.ac.rca.centrika.models.ReferenceNumber;
import rw.ac.rca.centrika.services.ReferenceNumberService;

import java.util.List;
import java.util.UUID;

public class ReferenceNumberServiceImpl implements ReferenceNumberService {
    @Override
    public List<ReferenceNumber> getAllReferenceNumbers() {
        return null;
    }

    @Override
    public ReferenceNumber getReferenceNumberById(UUID referenceNumberId) {
        return null;
    }

    @Override
    public ReferenceNumber createReferenceNumber(CreateReferenceNumberDTO referenceNumberDTO) {
        return null;
    }

    @Override
    public ReferenceNumber updateReferenceNumber(UUID referenceNumberId, CreateReferenceNumberDTO referenceNumberDTO) {
        return null;
    }

    @Override
    public ReferenceNumber deleteReferenceNumber(UUID referenceNumberId) {
        return null;
    }

    @Override
    public ReferenceNumber generateReferenceNumber(CreateReferenceNumberDTO referenceNumberDTO) {
        return null;
    }

    @Override
    public ReferenceNumber updateReferenceNumberStatus(UUID referenceNumberId, ERefNumStatus status) {
        return null;
    }

    @Override
    public ReferenceNumber updateReferenceNumberDestination(UUID referenceNumberId, String destination) {
        return null;
    }

    @Override
    public List<ReferenceNumber> getReferenceNumbersByDepartment(UUID departmentId) {
        return null;
    }

    @Override
    public List<ReferenceNumber> searchReferenceNumberByTitle(String title) {
        return null;
    }

    @Override
    public List<ReferenceNumber> searchReferenceNumberByDestination(String destination) {
        return null;
    }

    @Override
    public List<ReferenceNumber> searchReferenceNumberByStatus(ERefNumStatus status) {
        return null;
    }

    @Override
    public List<ReferenceNumber> searchReferenceNumberByTitleAndDestination(String title, String destination) {
        return null;
    }
}
