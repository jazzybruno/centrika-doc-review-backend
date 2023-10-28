package rw.ac.rca.centrika.services.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rw.ac.rca.centrika.dtos.requests.CreateAdminDTO;
import rw.ac.rca.centrika.dtos.requests.CreateUserDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateUserDTO;
import rw.ac.rca.centrika.enumerations.EStatus;
import rw.ac.rca.centrika.exceptions.BadRequestAlertException;
import rw.ac.rca.centrika.exceptions.NotFoundException;
import rw.ac.rca.centrika.models.Department;
import rw.ac.rca.centrika.models.User;
import rw.ac.rca.centrika.repositories.IDepartmentRepository;
import rw.ac.rca.centrika.repositories.IUserRepository;
import rw.ac.rca.centrika.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private IUserRepository userRepository;
    private IDepartmentRepository departmentRepository;
    @Value("adminKey")
    private String adminKey;

    @Autowired
    public UserServiceImpl(IUserRepository iUserRepository, IDepartmentRepository iDepartmentRepository) {
        this.userRepository = iUserRepository;
        this.departmentRepository = iDepartmentRepository;
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
            if(createUserDTO.getEmail().equals(adminKey)){
                Department department = departmentRepository.findById(createUserDTO.getDepartmentId()).orElseThrow(()-> {throw new NotFoundException("the Department was not found");});
                String activationCode = "123424";
                EStatus status =  EStatus.WAIT_EMAIL_VERIFICATION;
                User user = new User(
                        createUserDTO.getUsername(),
                        createUserDTO.getPhoneNumber(),
                        createUserDTO.getEmail(),
                        createUserDTO.getGender(),
                        activationCode,
                        status,
                        department
                )
            }else{
                throw new BadRequestAlertException("Unauthorized to perform this action");
            }
        }else{
            throw new BadRequestAlertException("The User with the provided email or phone Already Exists");
        }
    }

    @Override
    public User createAdmin(CreateAdminDTO createAdminDTO) {
        return null;
    }

    @Override
    public User updateUser(UUID userId, UpdateUserDTO updateUserDTO) {
        return null;
    }

    @Override
    public User deleteUser(UUID userId) {
        return null;
    }
}
