package com.a00n.controllers;

import java.util.ArrayList;
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
import com.a00n.entities.Student;
import com.a00n.exceptions.ApiRequestException;
import com.a00n.repositories.RoleRepository;
import com.a00n.services.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/students")
@CrossOrigin("*")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public List<Student> findAllStudents() {
        return studentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findOnStudent(@PathVariable int id) {
        Student student = studentService.findById(id);
        if (student == null) {
            throw new ApiRequestException("Student with ID " + id + " not found.", HttpStatus.BAD_REQUEST);
        } else {
            return ResponseEntity.ok(student);
        }
    }

    @PostMapping
    public Student createStudent(@Valid @RequestBody Student student, Errors errors) {
        System.out.println(student);
        if (errors.hasErrors()) {
            var fieldError = errors.getFieldError();
            if (fieldError != null) {
                throw new ApiRequestException(fieldError.getDefaultMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        try {
            List<Integer> roleIds = new ArrayList<>();
            for (Role role : student.getRoles()) {
                roleIds.add(role.getId());
            }

            List<Role> roles = roleRepository.findAllById(roleIds);
            student.setRoles(roles);
            // student.setFiliere(fili);
            return studentService.create(student);
        } catch (Exception ex) {
            throw new ApiRequestException(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable int id, @Valid @RequestBody Student student, Errors errors) {
        if (errors.hasErrors()) {
            var fieldError = errors.getFieldError();
            if (fieldError != null) {
                throw new ApiRequestException(fieldError.getDefaultMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        if (studentService.findById(id) == null) {
            throw new ApiRequestException("Student with ID " + id + " not found.", HttpStatus.BAD_REQUEST);
        } else {
            student.setId(id);
            Student u = studentService.update(student);
            if (u == null) {
                throw new ApiRequestException("Role id not found", HttpStatus.BAD_REQUEST);
            } else {
                return u;
            }
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable int id) {
        Student student = studentService.findById(id);
        if (student == null) {
            throw new ApiRequestException("Student with ID " + id + " not found.", HttpStatus.BAD_REQUEST);
        } else {
            studentService.delete(student);
            return ResponseEntity.ok("Student with ID " + id + " deleted successfully.");
        }
    }

    @PostMapping("/{id}/assign")
    public List<Student> assignRole(@PathVariable int id, @RequestBody List<Role> roles) {
        System.out.println(id);
        System.out.println(roles);
        Student student = studentService.findById(id);
        if (student == null) {
            throw new ApiRequestException("Student with ID " + id + " not found.", HttpStatus.BAD_REQUEST);
        } else {
            List<Integer> roleIds = new ArrayList<>();
            for (Role role : roles) {
                roleIds.add(role.getId());
            }

            List<Role> roles2 = roleRepository.findAllById(roleIds);
            student.setRoles(roles2);
            Student u = studentService.update(student);
            var list = new ArrayList<Student>();
            list.add(u);
            if (u == null) {
                throw new ApiRequestException("Role id not found", HttpStatus.BAD_REQUEST);
            } else {
                return list;
            }
        }
    }
}
