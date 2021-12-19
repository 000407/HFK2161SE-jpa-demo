package com.apiit.demo.jpa.controller;

import com.apiit.demo.jpa.controller.payload.request.NewMaterial;
import com.apiit.demo.jpa.controller.payload.response.InventoriedCourseMaterial;
import com.apiit.demo.jpa.controller.payload.response.StudentMaterialGrant;
import com.apiit.demo.jpa.model.CourseMaterial;
import com.apiit.demo.jpa.model.MaterialGrant;
import com.apiit.demo.jpa.model.MaterialInventory;
import com.apiit.demo.jpa.model.Student;
import com.apiit.demo.jpa.repo.CourseMaterialRepository;
import com.apiit.demo.jpa.repo.StudentRepository;
import com.apiit.demo.jpa.service.exception.InsufficientResourcesException;
import com.apiit.demo.jpa.service.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.log.LogMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/materials")
public class CourseMaterialController {

    private final CourseMaterialRepository courseMaterialRepository;
    private final StudentRepository studentRepository;
    private final EntityManager entityManager;

    @PostMapping("/new")
    @Transactional
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
        return ResponseEntity.ok(generateCourseMaterialResponse(courseMaterial));
    }

    @GetMapping("/all")
    public ResponseEntity<List<InventoriedCourseMaterial>> getAllCourses() {
        log.info("Retrieving all available course materials...");

        // Finding everything with JPQL
        // Same effect as calling courseMaterialRepository.findAll()
        List<CourseMaterial> result = entityManager
                .createQuery("SELECT m FROM CourseMaterial m", CourseMaterial.class)
                .getResultList();

        return ResponseEntity.ok(
                generateCourseMaterialListResponse(result)
        );
    }

    @PutMapping("/grant/{materialId}/toStudent/{studentId}")
    @Transactional
    public ResponseEntity<StudentMaterialGrant> grantMaterialToStudent(@PathVariable long materialId,
                                                                       @PathVariable long studentId) {
        log.info("Granting the material {}, to student {}...", materialId, studentId);

        final Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("No student was found for ID: " + studentId));

        final CourseMaterial courseMaterial = courseMaterialRepository.findById(materialId)
                .orElseThrow(() -> new NotFoundException("No course material was found for ID: " + materialId));

        if (courseMaterial.getInventory().checkIfCopiesAreUnavailable()) {
            final LogMessage message = LogMessage.format("Insufficient number of copies of the material.");
            log.info(message);
            throw new InsufficientResourcesException(message.toString());
        }

        student.getMaterialGrants().add(
                MaterialGrant.builder()
                        .student(student)
                        .courseMaterial(courseMaterial)
                        .issuedOn(LocalDate.now())
                        .build()
        );

        courseMaterial.getInventory().markOneCopyGranted();

        studentRepository.save(student);
        courseMaterialRepository.save(courseMaterial);

        final StudentMaterialGrant grant = StudentMaterialGrant.builder()
                .materialId(materialId)
                .studentId(studentId)
                .issuedOn(LocalDate.now())
                .build();

        return ResponseEntity.ok(grant);
    }

    private List<InventoriedCourseMaterial> generateCourseMaterialListResponse(List<CourseMaterial> courseMaterials) {
        log.info("Generating response for list of course materials...");

        final List<InventoriedCourseMaterial> responseList = new ArrayList<>();

        for (CourseMaterial material : courseMaterials) {
            responseList.add(generateCourseMaterialResponse(material));
        }

        return responseList;
    }

    private InventoriedCourseMaterial generateCourseMaterialResponse(CourseMaterial material) {
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
