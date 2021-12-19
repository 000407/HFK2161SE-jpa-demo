package com.apiit.demo.jpa.controller.payload.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class StudentMaterialGrant {
    private long studentId;
    private long materialId;
    private LocalDate issuedOn;
}
