package com.domain.store.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ImageDto {
    private Long imageId;
    private String fileName;
    private String downloadUrl;
}
