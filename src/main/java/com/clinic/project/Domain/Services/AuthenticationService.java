package com.clinic.project.Domain.Services;

import com.clinic.project.Domain.Model.JwtResponse;
import com.clinic.project.Domain.Model.Role;
import com.clinic.project.Domain.Model.User;
import com.clinic.project.infrastructure.exceptions.InvalidCredentialsException;
import com.clinic.project.infrastructure.exceptions.UserAlreadyExistsException;
import com.clinic.project.infrastructure.utils.JwtUtil;
import com.clinic.project.Adapters.Repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public JwtResponse authenticate(String username, String password) {
        // Fetch user from the database
        User user = userRepository.findByUsername(username);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        // Generate JWT token if credentials are valid
        String token = jwtUtil.generateToken(username);
        return new JwtResponse(token);
    }

    public String register(String username, String password) {
        // Check if user already exists
        if (userRepository.findByUsername(username) != null) {
            throw new UserAlreadyExistsException("User with username '" + username + "' already exists");
        }

        // Create a new user and save to the database
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password)); 
        newUser.setRole(Role.PATIENT); 
        userRepository.save(newUser);

        return "User registered successfully";
    }
}