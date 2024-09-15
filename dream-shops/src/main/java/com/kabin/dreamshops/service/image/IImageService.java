package com.kabin.dreamshops.service.image;

import com.kabin.dreamshops.dto.ImageDto;
import com.kabin.dreamshops.model.Image;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {

    Image getImageById(Long id);

    void deleteImageById(Long id);

   List <ImageDto> saveImages(List<MultipartFile> file, Long id);

    void updateImage(MultipartFile file, Long id);
}
