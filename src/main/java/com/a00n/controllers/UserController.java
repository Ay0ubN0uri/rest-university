package com.a00n.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a00n.entities.Role;
import com.a00n.entities.User;
import com.a00n.exceptions.ApiRequestException;
import com.a00n.repositories.RoleRepository;
import com.a00n.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public List<User> findAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findOnUser(@PathVariable int id) {
        User user = userService.findById(id);
        if (user == null) {
            throw new ApiRequestException("User with ID " + id + " not found.", HttpStatus.BAD_REQUEST);
        } else {
            return ResponseEntity.ok(user);
        }
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user, Errors errors) {
        System.out.println(user);
        if (errors.hasErrors()) {
            var fieldError = errors.getFieldError();
            if (fieldError != null) {
                throw new ApiRequestException(fieldError.getDefaultMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        try {
            List<Integer> roleIds = new ArrayList<>();
            for (Role role : user.getRoles()) {
                roleIds.add(role.getId());
            }

            List<Role> roles = roleRepository.findAllById(roleIds);
            user.setRoles(roles);
            return userService.create(user);
        } catch (Exception ex) {
            throw new ApiRequestException(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable int id, @Valid @RequestBody User user, Errors errors) {
        if (errors.hasErrors()) {
            var fieldError = errors.getFieldError();
            if (fieldError != null) {
                throw new ApiRequestException(fieldError.getDefaultMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        if (userService.findById(id) == null) {
            throw new ApiRequestException("User with ID " + id + " not found.", HttpStatus.BAD_REQUEST);
        } else {
            user.setId(id);
            User u = userService.update(user);
            if (u == null) {
                throw new ApiRequestException("Role id not found", HttpStatus.BAD_REQUEST);
            } else {
                return u;
            }
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable int id) {
        User user = userService.findById(id);
        if (user == null) {
            throw new ApiRequestException("User with ID " + id + " not found.", HttpStatus.BAD_REQUEST);
        } else {
            userService.delete(user);
            return ResponseEntity.ok("User with ID " + id + " deleted successfully.");
        }
    }
}
