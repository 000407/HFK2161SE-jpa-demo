package com.apiit.demo.jpa.repo;

import com.apiit.demo.jpa.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {
    @Override
    List<Student> findAll();
}
