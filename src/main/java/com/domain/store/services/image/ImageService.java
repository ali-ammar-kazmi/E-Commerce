package com.domain.store.services.image;

import com.domain.store.exception.FoundException;
import com.domain.store.model.Image;
import com.domain.store.model.ImageConfig;
import com.domain.store.model.Product;
import com.domain.store.repository.ImageConfigRepository;
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
    private final ImageConfigRepository imageConfigRepository;
    private final ProductService productService;

    @Override
    public ImageConfig getImageById(Long id) {
        return imageConfigRepository.findById(id).orElseThrow(()-> new FoundException("Image Not Found with id: "+ id));
    }

    @Override
    public ImageConfig updateImage(MultipartFile file, Long id) {
        try{
            ImageConfig imageConfig = getImageById(id);
            imageConfig.setFileName(file.getOriginalFilename());
            imageConfig.setFileType(file.getContentType());
            Image image = imageConfig.getImage();
            image.setImage(new SerialBlob(file.getBytes()));
            imageConfig.setImage(image);
            return imageConfigRepository.save(imageConfig);
        } catch (IOException | SQLException | FoundException e){
            throw new FoundException(e.getMessage());
        }
    }

    @Override
    public void deleteImage(Long id) {
        imageConfigRepository.findById(id).ifPresentOrElse(imageConfigRepository::delete, ()-> {throw new FoundException("Image Not Found with id: " + id);});
    }

    @Override
    public List<ImageConfig> saveImages(List<MultipartFile> files, Long id) {
        Product product = productService.getProductById(id);
        List<ImageConfig> images = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                ImageConfig imageConfig = new ImageConfig();
                imageConfig.setFileName(file.getOriginalFilename());
                imageConfig.setFileType(file.getContentType());
                Image image = new Image();
                image.setImage(new SerialBlob(file.getBytes()));
                imageConfig.setImage(image);
                imageConfig.setProduct(product);
                imageConfigRepository.save(imageConfig);
                String buildUrl = "/api/v1/image/download/";
                String downloadUrl = buildUrl + image.getId();
                imageConfig.setDownloadUrl(downloadUrl);
                image.setImageConfig(imageConfig);
                ImageConfig savedImage = imageConfigRepository.save(imageConfig);
                images.add(savedImage);
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return images;
    }
}
