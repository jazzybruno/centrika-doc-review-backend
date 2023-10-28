package rw.ac.rca.centrika.services.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.ac.rca.centrika.dtos.requests.CreateAdminDTO;
import rw.ac.rca.centrika.dtos.requests.CreateUserDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateUserDTO;
import rw.ac.rca.centrika.exceptions.NotFoundException;
import rw.ac.rca.centrika.models.User;
import rw.ac.rca.centrika.repositories.IDepartmentRepository;
import rw.ac.rca.centrika.repositories.IUserRepository;
import rw.ac.rca.centrika.services.UserService;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private IUserRepository userRepository;
    private IDepartmentRepository departmentRepository;

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
        return null;
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
