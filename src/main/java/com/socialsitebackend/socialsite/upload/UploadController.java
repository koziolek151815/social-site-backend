package com.socialsitebackend.socialsite.upload;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UploadController {

    private final UploadService uploadService;

    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @RequestMapping(value="/uploadPostPhoto", method = RequestMethod.POST)
    public ResponseEntity<String> uploadPhotoUrl(@RequestParam("file") MultipartFile file)  {
        try {
            String createdFilename = uploadService.uploadPostPhoto(file);
            return new ResponseEntity<>(createdFilename, HttpStatus.CREATED);
        } catch (RuntimeException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
