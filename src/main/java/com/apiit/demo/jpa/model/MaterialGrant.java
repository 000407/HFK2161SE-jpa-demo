package com.apiit.demo.jpa.model;

import com.apiit.demo.jpa.model.keys.MaterialGrantKey;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class MaterialGrant {

    /*
     * This is specially useful for the case where you are having attributes for the association.
     */

    @EmbeddedId
    MaterialGrantKey id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("materialId")
    @JoinColumn(name = "material_id")
    private CourseMaterial courseMaterial;

    private LocalDate issuedOn;
}
