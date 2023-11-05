package com.a00n.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.a00n.entities.Filiere;

@Repository
public interface FiliereRepository extends JpaRepository<Filiere, Integer> {

}
