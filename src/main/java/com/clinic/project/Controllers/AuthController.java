
package com.clinic.project.Controllers;

import com.clinic.project.Services.AuthenticationService;
import com.clinic.project.Model.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.clinic.project.Model.JwtResponse;

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