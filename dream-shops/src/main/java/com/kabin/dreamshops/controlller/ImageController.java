package com.kabin.dreamshops.controlller;

import com.kabin.dreamshops.dto.ImageDto;
import com.kabin.dreamshops.exception.ResourceNotFoundException;
import com.kabin.dreamshops.model.Image;
import com.kabin.dreamshops.response.ApiResponse;
import com.kabin.dreamshops.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@RestController
@RequestMapping("${api.prefix}/images")
@RequiredArgsConstructor
public class ImageController {

    private final IImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId) {
        try {
                List<ImageDto> imagesDtos = imageService.saveImages(files, productId);
                return ResponseEntity.ok(new ApiResponse("Upload Success!",imagesDtos ));
        }
        catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload failed!", e.getMessage()));
        }
    }


    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1,(int) image.getImage().length()));

        return  ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);
    }


    //update image
    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@RequestParam MultipartFile file, @PathVariable Long imageId) {

        try {
            Image image = imageService.getImageById(imageId);

            if (image != null) {
                imageService.updateImage(file, imageId);
                return ResponseEntity.ok(new ApiResponse("Image updated successfully!", image));
            }
        }
            catch (ResourceNotFoundException e) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
            }

            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Update Failed!",INTERNAL_SERVER_ERROR));

        }


    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage( @PathVariable Long imageId) {

        try {
            Image image = imageService.getImageById(imageId);

            if (image != null) {
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new ApiResponse("Image Deleted successfully!", image));
            }
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete Failed!",INTERNAL_SERVER_ERROR));

    }

}
