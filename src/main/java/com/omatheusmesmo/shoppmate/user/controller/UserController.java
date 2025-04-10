package com.omatheusmesmo.shoppmate.user.controller;

import com.omatheusmesmo.shoppmate.user.entity.User;
import com.omatheusmesmo.shoppmate.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserDetailsService userDetailsService;

    @Operation(summary = "Return all users")
    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.findUsers();
    }

    @Operation(summary = "Return a user by id")
    @GetMapping("/userDetailsService/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.findUser(id);
    }
}
