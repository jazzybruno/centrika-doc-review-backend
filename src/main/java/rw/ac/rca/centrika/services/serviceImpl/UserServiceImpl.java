package rw.ac.rca.centrika.services.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.rca.centrika.dtos.requests.CreateAdminDTO;
import rw.ac.rca.centrika.dtos.requests.CreateUserDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateUserDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateUserDepartmentDTO;
import rw.ac.rca.centrika.enumerations.ECategory;
import rw.ac.rca.centrika.enumerations.EStatus;
import rw.ac.rca.centrika.enumerations.EUserRole;
import rw.ac.rca.centrika.exceptions.BadRequestAlertException;
import rw.ac.rca.centrika.exceptions.InternalServerErrorException;
import rw.ac.rca.centrika.exceptions.NotFoundException;
import rw.ac.rca.centrika.models.Department;
import rw.ac.rca.centrika.models.Role;
import rw.ac.rca.centrika.models.User;
import rw.ac.rca.centrika.repositories.IDepartmentRepository;
import rw.ac.rca.centrika.repositories.IUserRepository;
import rw.ac.rca.centrika.services.UserService;
import rw.ac.rca.centrika.utils.HashUtil;
import rw.ac.rca.centrika.utils.Utility;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private IUserRepository userRepository;
    private IDepartmentRepository departmentRepository;
    private RoleServiceImpl roleService;
    private DepartmentServiceImp departmentServiceImp;
    @Value("${adminKey}")
    private String adminKey;

    @Autowired
    public UserServiceImpl(IUserRepository iUserRepository, IDepartmentRepository iDepartmentRepository, RoleServiceImpl roleService ) {
        this.userRepository = iUserRepository;
        this.departmentRepository = iDepartmentRepository;
        this.roleService = roleService;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(UUID uuid) {
        return userRepository.findById(uuid).orElseThrow(() -> {throw new NotFoundException("The Resource was not found");
        });
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> {throw new NotFoundException("The Resource was not found");
        });
    }

    @Override
    public User createUser(CreateUserDTO createUserDTO) {
        Optional<User> optionalUser = userRepository.findUserByEmailOrPhoneNumber(createUserDTO.getEmail() , createUserDTO.getPhoneNumber());
        if(optionalUser.isEmpty()){
                Department department = departmentRepository.findById(createUserDTO.getDepartmentId()).orElseThrow(()-> {throw new NotFoundException("the Department was not found");});
                String activationCode = Utility.randomUUID(6 , 0 , 'N');
                EStatus status =  EStatus.WAIT_EMAIL_VERIFICATION;
                Role role = roleService.getRoleByName(EUserRole.USER);
                createUserDTO.setPassword(HashUtil.hashPassword(createUserDTO.getPassword()));
                User user = new User(
                   createUserDTO.getUsername(),
                   createUserDTO.getPhoneNumber(),
                   createUserDTO.getEmail(),
                   createUserDTO.getGender(),
                   createUserDTO.getPassword(),
                   status,
                   false,
                   activationCode
                );
                Set<Role> roles = new HashSet<Role>();
                roles.add(role);
                user.setRoles(roles);
                try {
                    userRepository.save(user);
                    return user;
                }catch (Exception e){
                    throw new  InternalServerErrorException(e.getMessage());
                }
        }else{
            throw new BadRequestAlertException("The User with the provided email or phone Already Exists");
        }
    }

    @Override
    public User createAdmin(CreateAdminDTO createAdminDTO) {
        Optional<User> optionalUser = userRepository.findUserByEmailOrPhoneNumber(createAdminDTO.getEmail() , createAdminDTO.getPhoneNumber());
        if(optionalUser.isEmpty()){
            if(createAdminDTO.getRegistrationCode().equals(adminKey)){
                String activationCode = Utility.randomUUID(6 , 0 , 'N');
                EStatus status =  EStatus.WAIT_EMAIL_VERIFICATION;
                Role role = roleService.getRoleByName(EUserRole.ADMIN);
                createAdminDTO.setPassword(HashUtil.hashPassword(createAdminDTO.getPassword()));
                User user = new User(
                        createAdminDTO.getUsername(),
                        createAdminDTO.getPhoneNumber(),
                        createAdminDTO.getEmail(),
                        createAdminDTO.getGender(),
                        createAdminDTO.getPassword(),
                        status,
                        false,
                        activationCode
                );
                Set<Role> roles = new HashSet<Role>();
                roles.add(role);
                user.setRoles(roles);
                try {
                    userRepository.save(user);
                    return user;
                }catch (Exception e){
                    throw new  InternalServerErrorException(e.getMessage());
                }
            }else{
                throw new BadRequestAlertException("Unauthorized to perform this action");
            }
        }else{
            throw new BadRequestAlertException("The User with the provided email or phone Already Exists");
        }
    }

    @Override
    @Transactional
    public User updateUser(UUID userId, UpdateUserDTO updateUserDTO) {
        Optional<User> optionalUser = userRepository.findUserByEmailOrPhoneNumber(updateUserDTO.getEmail() , updateUserDTO.getPhoneNumber());
        if(optionalUser.isEmpty()){
            User user =  userRepository.findById(userId).orElseThrow(() -> {throw new NotFoundException("The Resource was not found");
            });
            try {
                user.setEmail(updateUserDTO.getEmail());
                user.setPhoneNumber(updateUserDTO.getPhoneNumber());
                user.setUsername(updateUserDTO.getUsername());
                return user;
            }catch (Exception e){
                throw new InternalServerErrorException(e.getMessage());
            }
        }else{
            throw new BadRequestAlertException("The email or password is already taken");
        }
    }

    @Override
    public User deleteUser(UUID userId) {
       User user = userRepository.findById(userId).orElseThrow(() -> {throw new NotFoundException("The Resource was not found");
        });
       try {
            userRepository.deleteById(userId);
            return user;
       }catch (Exception e){
           throw new InternalServerErrorException(e.getMessage());
       }
    }

    @Override
    @Transactional
    public User changeDepartment(UpdateUserDepartmentDTO updateUserDepartmentDTO) {
       User user = userRepository.findById(updateUserDepartmentDTO.getUserId()).orElseThrow(() -> {throw new NotFoundException("The Resource was not found");
       });
        Department department = departmentRepository.findById(updateUserDepartmentDTO.getDepartmentId()).orElseThrow(()-> {throw new NotFoundException("the Department was not found");});
        try {
            user.setDepartment(department);
            return user;
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<User> getUsersByDeptId(UUID deptId) {
        Department department = departmentServiceImp.getDepartmentById(deptId);
        try {
            return userRepository.findAllByDepartment(department);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
