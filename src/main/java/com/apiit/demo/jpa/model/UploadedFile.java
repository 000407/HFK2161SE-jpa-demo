package com.apiit.demo.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Blob;

@Entity
@Table(name = "uploaded_file")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadedFile {

    @Id
    @GeneratedValue
    private Long fileId;

    @Column(name = "hash")
    private String fileHash;

    @Lob
    private Blob content;
}
