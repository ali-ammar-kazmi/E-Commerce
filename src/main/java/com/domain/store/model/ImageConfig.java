package com.domain.store.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ImageConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileType;
    private String downloadUrl;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Product product;

    @OneToOne(mappedBy = "imageConfig", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Image image;
}
