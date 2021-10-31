package com.apiit.demo.jpa.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    void store(MultipartFile file);

//    Resource loadAsResource(String filename);
    default Resource loadAsResource(String filename) {
        throw new RuntimeException("Method not implemented!");
    }

    default byte[] loadFileBytes(Long id) {
        throw new RuntimeException("Method not implemented!");
    }
}
