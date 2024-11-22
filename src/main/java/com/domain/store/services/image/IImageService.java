package com.domain.store.services.image;

import com.domain.store.model.ImageConfig;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    ImageConfig getImageById(Long id);
    ImageConfig updateImage(MultipartFile file, Long id);
    void deleteImage(Long id);
    List<ImageConfig> saveImages(List<MultipartFile> files, Long id);
}
