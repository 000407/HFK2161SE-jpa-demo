package com.apiit.demo.jpa.controller;

import com.apiit.demo.jpa.controller.payload.request.NewMaterial;
import com.apiit.demo.jpa.controller.payload.response.InventoriedCourseMaterial;
import com.apiit.demo.jpa.model.CourseMaterial;
import com.apiit.demo.jpa.model.MaterialInventory;
import com.apiit.demo.jpa.repo.CourseMaterialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/materials")
public class CourseMaterialController {

    private final CourseMaterialRepository courseMaterialRepository;

    @PostMapping("/new")
    public ResponseEntity<InventoriedCourseMaterial> addNewMaterial(@RequestBody NewMaterial material) {
        log.info("Persisting new course material info: {}", material);
        CourseMaterial courseMaterial = CourseMaterial.builder()
                .name(material.getName())
                .type(material.getType())
                .build();

        courseMaterial = courseMaterialRepository.save(courseMaterial);

        MaterialInventory inventory = MaterialInventory.builder()
                .id(courseMaterial.getId())
                .courseMaterial(courseMaterial)
                .numberOfCopies(material.getNumberOfCopies())
                .build();

        courseMaterial.setInventory(inventory);

        courseMaterialRepository.save(courseMaterial);

        log.info("Completed persisting course material: {}", courseMaterial.getName());
        return ResponseEntity.ok(generateResponse(courseMaterial));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CourseMaterial>> getAllCourses() {
        log.info("Retrieving all available course materials...");
        return ResponseEntity.ok(courseMaterialRepository.findAll());
    }

    private InventoriedCourseMaterial generateResponse(CourseMaterial material) {
        log.info("Generating response: {}: {}", material.getId(), material.getName());

        return InventoriedCourseMaterial.builder()
                .id(material.getId())
                .inventoryId(material.getInventory().getId())
                .name(material.getName())
                .materialType(material.getType())
                .numberOfCopies(material.getInventory().getNumberOfCopies())
                .build();
    }
}
