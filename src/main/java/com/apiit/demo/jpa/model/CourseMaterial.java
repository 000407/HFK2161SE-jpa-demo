package com.apiit.demo.jpa.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CourseMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    /*
     * We don't need to refer to a list of students who received a particular material.
     * If we did, we would have enabled the following.
     */

//    @OneToMany(mappedBy = "courseMaterial")
//    List<MaterialGrant> materialGrants;
}
