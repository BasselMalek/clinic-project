
package com.clinic.project.Adapters.Controllers;

import com.clinic.project.Domain.Model.AuthRequest;
import com.clinic.project.Domain.Model.JwtResponse;
import com.clinic.project.Domain.Services.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public JwtResponse login(@RequestBody AuthRequest authRequest) {
        return authenticationService.authenticate(authRequest.getUsername(), authRequest.getPassword());
    }

    @PostMapping("/register")
    public String register(@RequestBody AuthRequest authRequest) {
        return authenticationService.register(authRequest.getUsername(), authRequest.getPassword());
    }
}