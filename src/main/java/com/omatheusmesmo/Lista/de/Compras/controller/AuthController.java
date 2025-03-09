package com.omatheusmesmo.Lista.de.Compras.controller;

import com.omatheusmesmo.Lista.de.Compras.dtos.LoginRequest;
import com.omatheusmesmo.Lista.de.Compras.entity.User;
import com.omatheusmesmo.Lista.de.Compras.service.JwtService;
import com.omatheusmesmo.Lista.de.Compras.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserService userService;
    @Autowired
    JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserDetailsService userDetailsService;

    @PostMapping("/register/userDetailsService")
    public ResponseEntity<User> registerUser(@RequestBody User user){
        userService.addUser(user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){

        UserDetails user = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(token);
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }
}
