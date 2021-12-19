package com.apiit.demo.jpa.service.impl;

import com.apiit.demo.jpa.model.UploadedFile;
import com.apiit.demo.jpa.repo.FileRepository;
import com.apiit.demo.jpa.service.StorageService;
import com.apiit.demo.jpa.service.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class DatabaseStorageService implements StorageService {
    private final EntityManagerFactory entityManagerFactory;
    private final FileRepository fileRepository;

    @Override
    public void store(MultipartFile file) {
        try {
            fileRepository.save(UploadedFile.builder()
                    .content(generateBlob(file))
                    .fileHash("SHA256_FILE_HASH")
                    .build());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] loadFileBytes(Long id) {
        try {
            UploadedFile uploadedFile = fileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("File not found with ID " + id));

            byte[] bytes = uploadedFile.getContent()
                    .getBytes(1, (int) uploadedFile.getContent().length());

            uploadedFile.getContent().free();

            return bytes;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Blob generateBlob(MultipartFile file) throws IOException {
        final Session session = (Session) entityManagerFactory.createEntityManager()
                .getDelegate();

        return Hibernate.getLobCreator(session)
                .createBlob(file.getInputStream(), file.getSize());
    }
}
