package rw.ac.rca.centrika.services;

import rw.ac.rca.centrika.dtos.requests.*;
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
    public User changeDepartment(UpdateUserDepartmentDTO updateUserDepartmentDTO);
    public List<User> getUsersByDeptId(UUID deptId);

    // inviting the user
    public User inviteUser(InviteUserDTO inviteUserDTO);
    public boolean isUserInvited(String email , String token);
}
