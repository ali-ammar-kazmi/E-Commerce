package com.domain.store.services.image;

import com.domain.store.dto.ImageDto;
import com.domain.store.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    public Image getImageById(Long id);
    public ImageDto updateImage(MultipartFile file, Long id);
    public void deleteImage(Long id);
    public List<ImageDto> saveImages(List<MultipartFile> files, Long id);
}
