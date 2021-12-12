package com.apiit.demo.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialInventory {
    @Id
    private long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private CourseMaterial courseMaterial;

    @Column(nullable = false)
    private long numberOfCopies;
}
