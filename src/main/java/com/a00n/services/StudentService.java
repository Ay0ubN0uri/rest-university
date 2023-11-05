package com.a00n.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a00n.dao.IDao;
import com.a00n.entities.Student;
import com.a00n.repositories.StudentRepository;

@Service
public class StudentService implements IDao<Student> {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student create(Student o) {
        o.setId(0);
        return studentRepository.save(o);
    }

    @Override
    public boolean delete(Student o) {
        try {
            studentRepository.delete(o);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Student update(Student o) {
        try {
            return studentRepository.save(o);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(int id) {
        return studentRepository.findById(id).orElse(null);
    }

}
