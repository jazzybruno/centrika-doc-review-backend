package rw.ac.rca.centrika.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.rca.centrika.dtos.requests.CreateAdminDTO;
import rw.ac.rca.centrika.dtos.requests.CreateUserDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateUserDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateUserDepartmentDTO;
import rw.ac.rca.centrika.models.User;
import rw.ac.rca.centrika.services.UserService;
import rw.ac.rca.centrika.utils.ApResponse;
import rw.ac.rca.centrika.utils.ApiResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched the users",
                users
        ));
    }

    @GetMapping("id/{userId}")
    public ResponseEntity getUserById(@PathVariable UUID userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched the user",
                user
        ));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched the user",
                user
        ));
    }

    @PostMapping("/create")
    public ResponseEntity createUser(@RequestBody CreateUserDTO createUserDTO) {
        User user = userService.createUser(createUserDTO);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully created the user",
                user
        ));
    }

    @PostMapping("/create-admin")
    public ResponseEntity createAdmin(@RequestBody CreateAdminDTO createAdminDTO) {
        User user = userService.createAdmin(createAdminDTO);
        return ResponseEntity.ok().body(
                new ApiResponse(
                        true,
                        "Successfully created the admin",
                        user
                )
        );
    }

    @PatchMapping("update/{userId}")
    public ResponseEntity updateUser(@PathVariable UUID userId, @RequestBody UpdateUserDTO updateUserDTO) {
        User user = userService.updateUser(userId, updateUserDTO);
        return ResponseEntity.ok(new ApiResponse(
                true,
                "Successfully updated the user",
                user
        ));
    }

    @DeleteMapping("delete/{userId}")
    public ResponseEntity deleteUser(@PathVariable UUID userId) {
        User user = userService.deleteUser(userId);
        return ResponseEntity.ok(new ApiResponse(
                true,
                "Successfully deleted the user",
                user
        ));
    }

    @PatchMapping("/change-department")
    public ResponseEntity changeDepartment(@RequestBody UpdateUserDepartmentDTO updateUserDepartmentDTO) {
        User user = userService.changeDepartment(updateUserDepartmentDTO);
        return ResponseEntity.ok(new ApiResponse(
                true,
                "Successfully changed the user's department",
                user
        ));
    }
}

