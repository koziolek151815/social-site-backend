package com.socialsitebackend.socialsite.image;

import com.socialsitebackend.socialsite.entities.ImageEntity;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class ImageService {

    public final ImageRepository imageRepository;

    public ImageEntity uploadImage(MultipartFile imageFile) throws IOException {
        if(imageFile==null)return null;

        //TODO: Add a check if the file is an image
        //if(imageFile.getContentType() == ?)

        ImageEntity imageEntity = ImageEntity.builder()
                .originalFilename(imageFile.getOriginalFilename())
                .bytes(imageFile.getBytes())
                .build();

        return imageRepository.save(imageEntity);
    }
}
