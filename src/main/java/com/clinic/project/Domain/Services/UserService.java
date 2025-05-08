package com.clinic.project.Domain.Services;

import com.clinic.project.Domain.Model.User;
import com.clinic.project.Adapters.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.clinic.project.Domain.Model.Role;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    
    public List<User> getDoctors() {
        return userRepository.findByRole(Role.DOCTOR);
    }
    

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}