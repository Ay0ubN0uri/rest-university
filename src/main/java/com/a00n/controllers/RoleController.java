package com.a00n.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a00n.entities.Role;
import com.a00n.exceptions.ApiRequestException;
import com.a00n.services.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/roles")
@CrossOrigin("*")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<Role> findAllRoles() {
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findOnRole(@PathVariable int id) {
        Role role = roleService.findById(id);
        if (role == null) {
            throw new ApiRequestException("Role with ID " + id + " not found.", HttpStatus.BAD_REQUEST);
        } else {
            return ResponseEntity.ok(role);
        }
    }

    @PostMapping
    public Role createRole(@Valid @RequestBody Role role, Errors errors) {
        if (errors.hasErrors()) {
            var fieldError = errors.getFieldError();
            if (fieldError != null) {
                throw new ApiRequestException(fieldError.getDefaultMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        try {
            return roleService.create(role);
        } catch (Exception ex) {
            throw new ApiRequestException(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public Role updateRole(@PathVariable int id, @Valid @RequestBody Role role, Errors errors) {
        if (errors.hasErrors()) {
            var fieldError = errors.getFieldError();
            if (fieldError != null) {
                throw new ApiRequestException(fieldError.getDefaultMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        if (roleService.findById(id) == null) {
            throw new ApiRequestException("Role with ID " + id + " not found.", HttpStatus.BAD_REQUEST);
        } else {
            role.setId(id);
            return roleService.update(role);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRole(@PathVariable int id) {
        Role role = roleService.findById(id);
        if (role == null) {
            throw new ApiRequestException("Role with ID " + id + " not found.", HttpStatus.BAD_REQUEST);
        } else {
            roleService.delete(role);
            return ResponseEntity.ok("Role with ID " + id + " deleted successfully.");
        }
    }
}
