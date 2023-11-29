package rw.ac.rca.centrika.services.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.rca.centrika.dtos.requests.CreateDepartmentDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateDepartmentDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateDocumentDTO;
import rw.ac.rca.centrika.enumerations.EUserRole;
import rw.ac.rca.centrika.exceptions.BadRequestAlertException;
import rw.ac.rca.centrika.exceptions.InternalServerErrorException;
import rw.ac.rca.centrika.exceptions.NotFoundException;
import rw.ac.rca.centrika.exceptions.UnAuthorizedException;
import rw.ac.rca.centrika.models.Department;
import rw.ac.rca.centrika.models.Role;
import rw.ac.rca.centrika.models.User;
import rw.ac.rca.centrika.repositories.IDepartmentRepository;
import rw.ac.rca.centrika.security.UserPrincipal;
import rw.ac.rca.centrika.services.DepartmentService;
import rw.ac.rca.centrika.utils.UserUtils;

import java.util.*;

@Service
public class DepartmentServiceImp implements DepartmentService {
    private final IDepartmentRepository departmentRepository;
    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    @Autowired
    public DepartmentServiceImp(IDepartmentRepository departmentRepository, UserServiceImpl userService, RoleServiceImpl roleService) {
        this.departmentRepository = departmentRepository;
        this.userService = userService;
        this.roleService = roleService;
    }
    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getDepartmentById(UUID deptId) {
        return departmentRepository.findById(deptId).orElseThrow(()-> {throw new NotFoundException("The department was not found");
        });
    }

    @Override
    public Department createDepartment(CreateDepartmentDTO createDepartmentDTO) {
        Optional<Department> optionalDepartment = departmentRepository.findDepartmentByName(createDepartmentDTO.getName());
        if(optionalDepartment.isEmpty()){
            if(UserUtils.isUserLoggedIn()){
                UserPrincipal userPrincipal = UserUtils.getLoggedInUser();
                assert userPrincipal != null;
                User user = userService.getUserById(userPrincipal.getId());
                Department department = new Department(
                        createDepartmentDTO.getName(),
                        createDepartmentDTO.getDescription(),
                        user
                );
                try {
                    departmentRepository.save(department);
                    return department;
                }catch (Exception e){
                    throw new InternalServerErrorException(e.getMessage());
                }
            }else{
                throw new UnAuthorizedException("You are not authorized");
            }
        }else{
            throw new BadRequestAlertException("The department already exists");
        }
    }

    @Override
    @Transactional
    public Department updateDepartment(UUID deptId, UpdateDepartmentDTO updateDepartmentDTO) {
        Optional<Department> optionalDepartment = departmentRepository.findDepartmentByName(updateDepartmentDTO.getName());
        if(optionalDepartment.isEmpty()){
            Department department = departmentRepository.findById(deptId).orElseThrow(()-> {throw new NotFoundException("The department was not found");
            });
            try {
                department.setName(department.getName());
                department.setDescription(department.getDescription());
                return department;
            }catch (Exception e){
                throw new InternalServerErrorException(e.getMessage());
            }
        }else{
            throw new BadRequestAlertException("The department already exists");
        }
    }

    @Override
    public Department deleteDepartment(UUID deptId) {
        Department department = departmentRepository.findById(deptId).orElseThrow(()-> {throw new NotFoundException("The department was not found");
        });
        try {
            departmentRepository.deleteById(deptId);
            return department;
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Department addDepartmentHead(UUID deptId, UUID userId) {
        Department department = departmentRepository.findById(deptId).orElseThrow(()-> {throw new NotFoundException("The department was not found");
        });
        User user = userService.getUserById(userId);
        Role role = roleService.getRoleByName(EUserRole.DEPARTMENT_HEAD);
        Role role1 = roleService.getRoleByName(EUserRole.USER);
        Set<Role> roleSet = new HashSet<Role>();
        if(department.getDepartmentHead() == null){
            if(user != null){
                department.setDepartmentHead(user);
                roleSet.add(role);
                user.setRoles(roleSet);
            }else{
                throw new NotFoundException("The user was not found");
            }
        }else{
            User head = department.getDepartmentHead();
            if(head.getId().equals(userId)) {
                throw new BadRequestAlertException("The user is already the department head");
            }else{
                Set<Role> roles = new HashSet<>();
                roles.add(role1);
                head.setRoles(roles);

                Set<Role> roles1 = new HashSet<>();
                roles1.add(role);
                user.setRoles(roles1);

                department.setDepartmentHead(user);
            }
        }

        try {
            return department;
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
