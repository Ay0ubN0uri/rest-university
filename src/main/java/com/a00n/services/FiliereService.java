package com.a00n.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a00n.dao.IDao;
import com.a00n.entities.Filiere;
import com.a00n.repositories.FiliereRepository;

@Service
public class FiliereService implements IDao<Filiere> {

    @Autowired
    private FiliereRepository filiereRepository;

    @Override
    public Filiere create(Filiere o) {
        o.setId(0);
        return filiereRepository.save(o);
    }

    @Override
    public boolean delete(Filiere o) {
        try {
            filiereRepository.delete(o);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Filiere update(Filiere o) {
        try {
            return filiereRepository.save(o);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Filiere> findAll() {
        return filiereRepository.findAll();
    }

    @Override
    public Filiere findById(int id) {
        return filiereRepository.findById(id).orElse(null);
    }

}
