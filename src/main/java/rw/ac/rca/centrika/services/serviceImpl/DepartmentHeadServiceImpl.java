package rw.ac.rca.centrika.services.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.rca.centrika.dtos.CreateDepartmentHeadDTO;
import rw.ac.rca.centrika.exceptions.InternalServerErrorException;
import rw.ac.rca.centrika.exceptions.NotFoundException;
import rw.ac.rca.centrika.models.Department;
import rw.ac.rca.centrika.models.DepartmentHead;
import rw.ac.rca.centrika.models.User;
import rw.ac.rca.centrika.repositories.IDepartmentHeadRepository;
import rw.ac.rca.centrika.repositories.IDepartmentRepository;
import rw.ac.rca.centrika.repositories.IUserRepository;
import rw.ac.rca.centrika.services.DepartmentHeadService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DepartmentHeadServiceImpl implements DepartmentHeadService {

    private IDepartmentHeadRepository departmentHeadRepository;
    private IUserRepository userRepository;
    private IDepartmentRepository departmentRepository;
    private DepartmentHead departmentHead;
    private List<DepartmentHead> departmentHeads;

    public DepartmentHeadServiceImpl(IDepartmentHeadRepository departmentHeadRepository, IUserRepository userRepository, IDepartmentRepository departmentRepository) {
        this.departmentHeadRepository = departmentHeadRepository;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<DepartmentHead> getAllDepartmentHeads() {
        try {
            return departmentHeadRepository.findAll();
        }catch (Exception e){
            throw new InternalServerErrorException("Error while getting all department heads");
        }
    }

    @Override
    public DepartmentHead getDepartmentHeadById(UUID departmentHeadId) {
        try {
            return departmentHeadRepository.findById(departmentHeadId).orElseThrow(() -> new InternalServerErrorException("Department head not found"));
        }catch (Exception e){
            throw new InternalServerErrorException("Error while getting department head by id");
        }
    }

    @Override
    public DepartmentHead createDepartmentHead(CreateDepartmentHeadDTO departmentHeadDTO) {
        Department department = departmentRepository.findById(departmentHeadDTO.getDepartmentId()).orElseThrow(() -> new NotFoundException("Department not found"));
        User user = userRepository.findById(departmentHeadDTO.getUserId()).orElseThrow(() -> new NotFoundException("User not found"));
        departmentHead = new DepartmentHead();
        departmentHead.setDepartmentId(department);
        departmentHead.setUserId(user);
        try {
            return departmentHeadRepository.save(departmentHead);
        }catch (Exception e){
            throw new InternalServerErrorException("Error while creating department head");
        }
    }

    @Override
    @Transactional
    public DepartmentHead updateDepartmentHead(UUID departmentHeadId, CreateDepartmentHeadDTO departmentHeadDTO) {
        departmentHead = getDepartmentHeadById(departmentHeadId);
        try {
        Department department = departmentRepository.findById(departmentHeadDTO.getDepartmentId()).orElseThrow(() -> new NotFoundException("Department not found"));
        User user = userRepository.findById(departmentHeadDTO.getUserId()).orElseThrow(() -> new NotFoundException("User not found"));
        departmentHead.setDepartmentId(department);
        departmentHead.setUserId(user);
        return departmentHead;
        }catch (Exception e){
            throw new InternalServerErrorException("Error while updating department head");
        }
    }

    @Override
    public DepartmentHead deleteDepartmentHead(UUID departmentHeadId) {
        departmentHead = getDepartmentHeadById(departmentHeadId);
        try {
            departmentHeadRepository.delete(departmentHead);
            return departmentHead;
        }catch (Exception e){
            throw new InternalServerErrorException("Error while deleting department head");
        }
    }

    @Override
    public DepartmentHead getAllDepartmentHeadByDepartmentId(UUID departmentId) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new NotFoundException("Department not found"));
        try {
            return departmentHeadRepository.findDepartmentHeadByDepartmentId(department);
        }catch (Exception e){
            throw new InternalServerErrorException("Error while getting department head by department id");
        }
    }

    @Override
    public DepartmentHead getDepartmentHeadByUserId(UUID userId) {
        try {
            return departmentHeadRepository.findDepartmentHeadByUserId(userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found")));
        }catch (Exception e){
            throw new InternalServerErrorException("Error while getting department head by user id");
        }
    }
}
