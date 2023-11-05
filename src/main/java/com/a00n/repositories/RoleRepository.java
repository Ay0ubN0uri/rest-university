package com.a00n.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.a00n.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
