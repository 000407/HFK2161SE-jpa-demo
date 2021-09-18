package com.apiit.demo.jpa.model.keys;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class MaterialGrantKey implements Serializable {

    @Column(name = "student_id")
    Long studentId;

    @Column(name = "material_id")
    Long materialId;
}
