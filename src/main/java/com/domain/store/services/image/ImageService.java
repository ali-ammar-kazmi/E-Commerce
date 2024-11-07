package com.domain.store.services.image;

import com.domain.store.dto.ImageDto;
import com.domain.store.exception.FoundException;
import com.domain.store.model.Image;
import com.domain.store.model.Product;
import com.domain.store.repository.ImageRepository;
import com.domain.store.services.product.ProductService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{
    private final ImageRepository imageRepository;
    private final ProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(()-> new FoundException("Image Not Found with id: "+ id));
    }

    @Override
    public ImageDto updateImage(MultipartFile file, Long id) {
        try{
            Image image = getImageById(id);
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
            ImageDto imageDto = new ImageDto();
            imageDto.setImageId(image.getId());
            imageDto.setFileName(image.getFileName());
            imageDto.setDownloadUrl(image.getDownloadUrl());
            return imageDto;
        } catch (IOException | SQLException | FoundException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteImage(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, ()-> {throw new FoundException("Image Not Found with id: " + id);});
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long id) {
        Product product = productService.getProductById(id);
        List<ImageDto> imageDtos = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);
                imageRepository.save(image);
                String buildUrl = "/api/v1/image/download/";
                String downloadUrl = buildUrl + image.getId();
                image.setDownloadUrl(downloadUrl);
                Image savedImage = imageRepository.save(image);


                ImageDto dto = new ImageDto();
                dto.setImageId(savedImage.getId());
                dto.setFileName(savedImage.getFileName());
                dto.setDownloadUrl(savedImage.getDownloadUrl());
                imageDtos.add(dto);

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return imageDtos;
    }
}
