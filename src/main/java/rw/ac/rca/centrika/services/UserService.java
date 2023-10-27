package rw.ac.rca.centrika.services;

import rw.ac.rca.centrika.models.User;

import java.util.List;

public interface UserService {
    public List<User> getAllUsers();
    public User getUserById();
    public User getUserByEmail();
    public User createUser();
    public User createAdmin();
    public User updateUser();
    public User deleteUser();
}
