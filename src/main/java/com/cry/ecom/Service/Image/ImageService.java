package com.cry.ecom.Service.Image;

import com.cry.ecom.Dto.ImageDto;
import com.cry.ecom.Entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    Image getImageById(Long id);

    void deleteImageById(Long id);

    List<ImageDto> saveImages(Long productId, List<MultipartFile> files);

    void updateImage(MultipartFile file, Long imageId);
}
