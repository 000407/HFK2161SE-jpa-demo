package com.apiit.demo.jpa.model;

import com.apiit.demo.jpa.model.keys.MaterialGrantKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialGrant {

    /*
     * This is specially useful for the case where you are having attributes for the association.
     */

    @EmbeddedId
    private MaterialGrantKey id = new MaterialGrantKey();

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
