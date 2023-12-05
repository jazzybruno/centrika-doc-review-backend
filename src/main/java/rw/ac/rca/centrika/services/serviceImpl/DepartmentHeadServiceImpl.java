package rw.ac.rca.centrika.services.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.rca.centrika.dtos.CreateDepartmentHeadDTO;
import rw.ac.rca.centrika.dtos.UpdateDepartmentHeadDTO;
import rw.ac.rca.centrika.enumerations.EUserRole;
import rw.ac.rca.centrika.exceptions.InternalServerErrorException;
import rw.ac.rca.centrika.exceptions.NotFoundException;
import rw.ac.rca.centrika.models.Department;
import rw.ac.rca.centrika.models.DepartmentHead;
import rw.ac.rca.centrika.models.Role;
import rw.ac.rca.centrika.models.User;
import rw.ac.rca.centrika.repositories.IDepartmentHeadRepository;
import rw.ac.rca.centrika.repositories.IDepartmentRepository;
import rw.ac.rca.centrika.repositories.IUserRepository;
import rw.ac.rca.centrika.services.DepartmentHeadService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DepartmentHeadServiceImpl implements DepartmentHeadService {
    private IDepartmentHeadRepository departmentHeadRepository;
    private IUserRepository userRepository;
    private IDepartmentRepository departmentRepository;
    private DepartmentHead departmentHead;
    private List<DepartmentHead> departmentHeads;
    private RoleServiceImpl roleService;

    @Autowired
    public DepartmentHeadServiceImpl(RoleServiceImpl roleService , IDepartmentHeadRepository departmentHeadRepository, IUserRepository userRepository, IDepartmentRepository departmentRepository) {
        this.departmentHeadRepository = departmentHeadRepository;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.roleService = roleService;
    }

    @Override
    public List<DepartmentHead> getAllDepartmentHeads() {
        try {
            return departmentHeadRepository.findAll();
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public DepartmentHead getDepartmentHeadById(UUID departmentHeadId) {
        try {
            return departmentHeadRepository.findById(departmentHeadId).orElseThrow(() -> new NotFoundException("Department head not found"));
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
        Role role = roleService.getRoleByName(EUserRole.DEPARTMENT_HEAD);
        Set<Role> roles = new HashSet<Role>();
        roles.add(role);
        user.setRoles(roles);
        try {
            return departmentHeadRepository.save(departmentHead);
        }catch (Exception e){
            throw new InternalServerErrorException("Error while creating department head");
        }
    }

    @Override
    @Transactional
    public DepartmentHead updateDepartmentHead(UUID departmentHeadId, UpdateDepartmentHeadDTO departmentHeadDTO) {
        departmentHead = getDepartmentHeadById(departmentHeadId);
        try {
        Department department = departmentRepository.findById(departmentHeadDTO.getDepartmentId()).orElseThrow(() -> new NotFoundException("Department not found"));
        User user = userRepository.findById(departmentHeadDTO.getUserId()).orElseThrow(() -> new NotFoundException("User not found"));
        User usualDeptHead = departmentHead.getUserId();

            Role user_role = roleService.getRoleByName(EUserRole.USER);
            Set<Role> roles_user = new HashSet<Role>();
            roles_user.add(user_role);
            usualDeptHead.setRoles(roles_user);

            Role dept_role = roleService.getRoleByName(EUserRole.DEPARTMENT_HEAD);
            Set<Role> dept_roles = new HashSet<Role>();
            dept_roles.add(dept_role);
            user.setRoles(dept_roles);

        departmentHead.setDepartmentId(department);
        departmentHead.setUserId(user);

        userRepository.save(usualDeptHead);
        userRepository.save(user);
        departmentHeadRepository.save(departmentHead);

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
