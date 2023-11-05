package com.a00n.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Student extends User {
    @Column(nullable = false)
    @NotNull(message = "User password cannot be empty")
    private String firstName;
    @Column(nullable = false)
    @NotNull(message = "User password cannot be empty")
    private String lastName;
    @Column(nullable = false)
    @NotNull(message = "User password cannot be empty")
    private String phoneNumber;

    @ManyToOne(optional = false)
    private Filiere filiere;

}
