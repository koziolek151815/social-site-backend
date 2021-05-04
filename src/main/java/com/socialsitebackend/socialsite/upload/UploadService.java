package com.socialsitebackend.socialsite.upload;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UploadService {

    public String uploadPostPhoto(MultipartFile file) throws IOException {
        if (file == null) {
            throw new FileNotFoundException();
        }
        String originalName = file.getOriginalFilename();

        Path currentDir = Paths.get(".");
        String imageDirectory = currentDir.toAbsolutePath() + "/photos/";
        makeDirectoryIfNotExist(imageDirectory);

        Path fileNamePath = Paths.get(imageDirectory, originalName);
        Files.write(fileNamePath, file.getBytes());

        return originalName;
    }

    private void makeDirectoryIfNotExist(String imageDirectory) {
        File directory = new File(imageDirectory);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
}
