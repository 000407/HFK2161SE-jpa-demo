package com.apiit.demo.jpa.repo;

import com.apiit.demo.jpa.model.CourseMaterial;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseMaterialRepository extends CrudRepository<CourseMaterial, Long> {
    @Override
    List<CourseMaterial> findAll();
}
