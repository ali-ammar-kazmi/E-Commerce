package com.domain.store.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Image {
    @Id
    @GeneratedValue
    private Long id;
    private String fileName;
    private String fileType;
    private String downloadUrl;
    @Lob
    private Blob image;

    @ManyToOne()
    @JoinColumn
    private Product product;
}
