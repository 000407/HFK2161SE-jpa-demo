package com.apiit.demo.jpa.controller.payload.response;

import com.apiit.demo.jpa.model.enumerated.MaterialType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoriedCourseMaterial {
    private long id;
    private long inventoryId;
    private String name;
    private MaterialType materialType;
    private long numberOfCopies;
}
