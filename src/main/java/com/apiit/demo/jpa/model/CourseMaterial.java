package com.apiit.demo.jpa.model;

import com.apiit.demo.jpa.model.enumerated.MaterialType;
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
public class CourseMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private MaterialType type;

    @OneToOne(mappedBy = "courseMaterial", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private MaterialInventory inventory;

    /*
     * We don't need to refer to a list of students who received a particular material.
     * If we did, we would have enabled the following.
     */

//    @OneToMany(mappedBy = "courseMaterial")
//    List<MaterialGrant> materialGrants;
}
