package rw.ac.rca.centrika.services.serviceImpl;

import jdk.jshell.execution.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.ac.rca.centrika.dtos.CreateReferenceNumberDTO;
import rw.ac.rca.centrika.enumerations.ERefNumStatus;
import rw.ac.rca.centrika.exceptions.BadRequestAlertException;
import rw.ac.rca.centrika.exceptions.InternalServerErrorException;
import rw.ac.rca.centrika.models.Department;
import rw.ac.rca.centrika.models.ReferenceNumber;
import rw.ac.rca.centrika.models.User;
import rw.ac.rca.centrika.repositories.IReferenceNumberRepository;
import rw.ac.rca.centrika.services.DepartmentService;
import rw.ac.rca.centrika.services.ReferenceNumberService;
import rw.ac.rca.centrika.services.UserService;
import rw.ac.rca.centrika.utils.Utility;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ReferenceNumberServiceImpl implements ReferenceNumberService {
    private final IReferenceNumberRepository referenceNumberRepository;
    private final UserService userService;
    private final DepartmentService departmentService;

    @Autowired
    public ReferenceNumberServiceImpl(IReferenceNumberRepository referenceNumberRepository, UserService userService, DepartmentService departmentService) {
        this.referenceNumberRepository = referenceNumberRepository;
        this.userService = userService;
        this.departmentService = departmentService;
    }
    @Override
    public List<ReferenceNumber> getAllReferenceNumbers() {
        try {
            return referenceNumberRepository.findAll();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ReferenceNumber getReferenceNumberById(UUID referenceNumberId) {
        try {
            return referenceNumberRepository.findById(referenceNumberId).orElseThrow(()->new InternalServerErrorException("Reference Number not found"));
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ReferenceNumber createReferenceNumber(CreateReferenceNumberDTO referenceNumberDTO) {
        User user = userService.getUserById(referenceNumberDTO.getCreatedBy());
        ReferenceNumber referenceNumber = new ReferenceNumber();
        int currentNum = referenceNumberRepository.findAll().size();
        String referenceNumberString = Utility.generateReferenceNumber(currentNum);
        try {
            referenceNumber.setReferenceNumber(referenceNumberString);
            referenceNumber.setTitle(referenceNumberDTO.getTitle());
            referenceNumber.setDestination(referenceNumberDTO.getDestination());
            referenceNumber.setStatus(referenceNumberDTO.getStatus());
            referenceNumber.setCreatedBy(user);
            referenceNumber.setCreatedAt(new Date());
            return referenceNumberRepository.save(referenceNumber);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }


    @Override
    public ReferenceNumber deleteReferenceNumber(UUID referenceNumberId) {
        ReferenceNumber referenceNumber = getReferenceNumberById(referenceNumberId);
        boolean hasNextOne = !referenceNumberRepository.findAll().get(referenceNumberRepository.findAll().size() - 1).equals(referenceNumber);
        boolean dayExpired = referenceNumber.getCreatedAt().after(new Date());

        System.out.printf("hasNextOne: %s, dayExpired: %s", hasNextOne, dayExpired);
        if(!hasNextOne && !dayExpired){
            referenceNumberRepository.delete(referenceNumber);
        }else{
            throw new BadRequestAlertException("You can't delete this reference number but you can change its status");
        }

        try {
            return referenceNumber;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }


    @Override
    public ReferenceNumber updateReferenceNumberStatus(UUID referenceNumberId, ERefNumStatus status) {
        ReferenceNumber referenceNumber = getReferenceNumberById(referenceNumberId);
        try {
            referenceNumber.setStatus(status);
            return referenceNumberRepository.save(referenceNumber);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public ReferenceNumber updateReferenceNumberDestination(UUID referenceNumberId, String destination) {
        ReferenceNumber referenceNumber = getReferenceNumberById(referenceNumberId);
        try {
            referenceNumber.setDestination(destination);
            return referenceNumberRepository.save(referenceNumber);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<ReferenceNumber> getReferenceNumbersByDepartment(UUID departmentId) {
        Department department = departmentService.getDepartmentById(departmentId);
        try {
            return referenceNumberRepository.findAllByDepartment(department);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<ReferenceNumber> searchReferenceNumberByDestination(String destination) {
        try {
            return referenceNumberRepository.searchByDestination(destination);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }


    @Override
    public List<ReferenceNumber> searchReferenceNumberByStatus(ERefNumStatus status) {
        try {
            return referenceNumberRepository.findAllByStatus(status);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<ReferenceNumber> getReferenceNumbersByUser(UUID userId) {
        User user = userService.getUserById(userId);
        try {
            return referenceNumberRepository.findAllByCreatedBy(user);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

}
