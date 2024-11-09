package com.domain.store.services.image;

import com.domain.store.model.ImageConfig;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    public ImageConfig getImageById(Long id);
    public ImageConfig updateImage(MultipartFile file, Long id);
    public void deleteImage(Long id);
    public List<ImageConfig> saveImages(List<MultipartFile> files, Long id);
}
