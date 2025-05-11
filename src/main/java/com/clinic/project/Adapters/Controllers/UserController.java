package com.clinic.project.Adapters.Controllers;

import com.clinic.project.Adapters.Dto.RoleChangeDTO;
import com.clinic.project.Domain.Model.Role;
import com.clinic.project.Domain.Model.User;
import com.clinic.project.Domain.Services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/doctors")
    public List<User> getDoctors() {
        userService.getDoctors().forEach(user -> user.setPassword(null));
        return userService.getDoctors();
    }


    @PutMapping("/{userId}/role")
    public String changeUserRole(@PathVariable Long userId, @RequestBody RoleChangeDTO roleChangeDTO) {
        try {
            userService.changeUserRole(userId, roleChangeDTO.getRole());
            return "User role updated successfully.";
        } catch (Exception e) {
            return "Error updating user role: " + e.getMessage();
        }
    }

}