package com.a00n.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a00n.dao.IDao;
import com.a00n.entities.Role;
import com.a00n.repositories.RoleRepository;

@Service
public class RoleService implements IDao<Role> {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role create(Role o) {
        o.setId(0);
        return roleRepository.save(o);
    }

    @Override
    public boolean delete(Role o) {
        try {
            roleRepository.delete(o);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Role update(Role o) {
        return roleRepository.save(o);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(int id) {
        return roleRepository.findById(id).orElse(null);
    }

}
