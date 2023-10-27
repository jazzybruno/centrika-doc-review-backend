package rw.ac.rca.centrika.services.serviceImpl;

import rw.ac.rca.centrika.dtos.CreateAdminDTO;
import rw.ac.rca.centrika.dtos.CreateUserDTO;
import rw.ac.rca.centrika.dtos.UpdateUserDTO;
import rw.ac.rca.centrika.models.User;
import rw.ac.rca.centrika.repositories.IUserRepository;
import rw.ac.rca.centrika.services.UserService;

import java.util.List;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public User getUserById(UUID uuid) {
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
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
