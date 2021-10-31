package com.apiit.demo.jpa.repo;

import com.apiit.demo.jpa.model.UploadedFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends CrudRepository<UploadedFile, Long> {
}
