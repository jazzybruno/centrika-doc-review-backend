package rw.ac.rca.centrika.services;

import rw.ac.rca.centrika.dtos.requests.CreateAdminDTO;
import rw.ac.rca.centrika.dtos.requests.CreateUserDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateUserDTO;
import rw.ac.rca.centrika.models.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    public List<User> getAllUsers();
    public User getUserById(UUID uuid);
    public User getUserByEmail(String email);
    public User createUser(CreateUserDTO createUserDTO);
    public User createAdmin(CreateAdminDTO createAdminDTO);
    public User updateUser(UUID userId , UpdateUserDTO updateUserDTO);
    public User deleteUser(UUID userId);
}
