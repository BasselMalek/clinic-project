
package com.clinic.project.Adapters.Controllers;

import com.clinic.project.Adapters.Dto.AuthRequest;
import com.clinic.project.Domain.Model.JwtResponse;
import com.clinic.project.Domain.Services.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public JwtResponse login(@RequestBody AuthRequest authRequest) {
        return authenticationService.authenticate(authRequest.getEmail(), authRequest.getPassword());
    }

    @PostMapping("/register")
    public String register(@RequestBody AuthRequest authRequest) {
        return authenticationService.register(authRequest.getUsername(), authRequest.getPassword(),authRequest.getEmail());
    }
}