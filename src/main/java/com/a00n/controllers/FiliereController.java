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

import com.a00n.entities.Filiere;
import com.a00n.exceptions.ApiRequestException;
import com.a00n.services.FiliereService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/filieres")
@CrossOrigin("*")
public class FiliereController {
    @Autowired
    private FiliereService filiereService;

    @GetMapping
    public List<Filiere> findAllFilieres() {
        return filiereService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findOnFiliere(@PathVariable int id) {
        Filiere filiere = filiereService.findById(id);
        if (filiere == null) {
            throw new ApiRequestException("Filiere with ID " + id + " not found.", HttpStatus.BAD_REQUEST);
        } else {
            return ResponseEntity.ok(filiere);
        }
    }

    @PostMapping
    public Filiere createFiliere(@Valid @RequestBody Filiere filiere, Errors errors) {
        if (errors.hasErrors()) {
            var fieldError = errors.getFieldError();
            if (fieldError != null) {
                throw new ApiRequestException(fieldError.getDefaultMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        try {
            return filiereService.create(filiere);
        } catch (Exception ex) {
            throw new ApiRequestException(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public Filiere updateFiliere(@PathVariable int id, @Valid @RequestBody Filiere filiere, Errors errors) {
        if (errors.hasErrors()) {
            var fieldError = errors.getFieldError();
            if (fieldError != null) {
                throw new ApiRequestException(fieldError.getDefaultMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        if (filiereService.findById(id) == null) {
            throw new ApiRequestException("Filiere with ID " + id + " not found.", HttpStatus.BAD_REQUEST);
        } else {
            filiere.setId(id);
            return filiereService.update(filiere);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFiliere(@PathVariable int id) {
        Filiere filiere = filiereService.findById(id);
        if (filiere == null) {
            throw new ApiRequestException("Filiere with ID " + id + " not found.", HttpStatus.BAD_REQUEST);
        } else {
            filiereService.delete(filiere);
            return ResponseEntity.ok("Filiere with ID " + id + " deleted successfully.");
        }
    }
}
