package com.domain.store.controller;

import com.domain.store.exception.StoreException;
import com.domain.store.model.ImageConfig;
import com.domain.store.response.StoreResponse;
import com.domain.store.services.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/image")
public class ImageController {
    private final IImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<StoreResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId){
        try{
            List<ImageConfig> images = imageService.saveImages(files, productId);
            return ResponseEntity.ok(new StoreResponse("Image Upload Success!", images));
        } catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new StoreResponse("Image Upload Failed!", INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/download/{imageId}")
    public ResponseEntity<ByteArrayResource> downloadImage(@PathVariable Long imageId){
        try{
            ImageConfig image = imageService.getImageById(imageId);
            ByteArrayResource resource = new ByteArrayResource(image.getImage().getImage().getBytes(1, (int) image.getImage().getImage().length()));
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"").body(resource);
        } catch ( StoreException e){
            return ResponseEntity.status(NOT_FOUND).body(null);
        } catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/update/{imageId}")
    public ResponseEntity<StoreResponse> updateImage(@RequestParam MultipartFile file, @PathVariable Long imageId){
        try{
            ImageConfig image = imageService.getImageById(imageId);
            if (image != null){
                ImageConfig updatedImage = imageService.updateImage(file, imageId);
                return ResponseEntity.ok(new StoreResponse("Update Success!", updatedImage));
            }
        } catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new StoreResponse("Update Failed!", INTERNAL_SERVER_ERROR));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new StoreResponse("Update Failed!", INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/delete/{imageId}")
    public ResponseEntity<StoreResponse> deleteImage(@PathVariable Long imageId){
        try{
            ImageConfig image = imageService.getImageById(imageId);
            if (image != null){
                imageService.deleteImage(imageId);
                return ResponseEntity.ok(new StoreResponse("Delete Success!", null));
            }
        } catch (Exception e){
            return ResponseEntity.status(NOT_FOUND).body(new StoreResponse("Delete Failed!", NOT_FOUND));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new StoreResponse("Delete Failed!", INTERNAL_SERVER_ERROR));
    }
}
