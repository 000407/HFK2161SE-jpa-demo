package com.apiit.demo.jpa.controller.payload.request;

import com.apiit.demo.jpa.model.enumerated.MaterialType;
import lombok.Data;

@Data
public class NewMaterial {
    private String name;
    private MaterialType type;
    private long numberOfCopies;
}
