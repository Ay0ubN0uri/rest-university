package com.a00n.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a00n.dao.IDao;
import com.a00n.entities.User;
import com.a00n.repositories.UserRepository;

@Service
public class UserService implements IDao<User> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User create(User o) {
        o.setId(0);
        return userRepository.save(o);
    }

    @Override
    public boolean delete(User o) {
        try {
            userRepository.delete(o);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public User update(User o) {
        try {
            return userRepository.save(o);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

}
