package com.domain.store.controller;

import com.domain.store.dto.ImageDto;
import com.domain.store.model.Image;
import com.domain.store.response.ApiResponse;
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
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId){
        try{
            List<ImageDto> imageDtos = imageService.saveImages(files, productId);
            return ResponseEntity.ok(new ApiResponse("Image Upload Success!", imageDtos));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Image Upload Failed!", e.getMessage()));
        }
    }

    @GetMapping("/download/{imageId}")
    public ResponseEntity<ByteArrayResource> downloadImage(@PathVariable Long imageId){
        try{
            Image image = imageService.getImageById(imageId);
            ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"").body(resource);
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/update/{imageId}")
    public ResponseEntity<ApiResponse> updateImage(@RequestParam MultipartFile file, @PathVariable Long imageId){
        try{
            Image image = imageService.getImageById(imageId);
            if (image != null){
                ImageDto imageDto = imageService.updateImage(file, imageId);
                return ResponseEntity.ok(new ApiResponse("Update Success!", imageDto));
            }
        } catch (Exception e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Update Failed!", INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/delete/{imageId}")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId){
        try{
            Image image = imageService.getImageById(imageId);
            if (image != null){
                imageService.deleteImage(imageId);
                return ResponseEntity.ok(new ApiResponse("Delete Success!", null));
            }
        } catch (Exception e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete Failed!", INTERNAL_SERVER_ERROR));
    }
}
