package com.domain.store.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileType;
    private String downloadUrl;
    @Lob
    private Blob image;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Product product;
}
