package com.skillbox.diplom.service;

import com.skillbox.diplom.util.FileStorageProperties;
import com.skillbox.diplom.util.GenerateHash;
import com.skillbox.diplom.util.ImageConverter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringJoiner;

@RequiredArgsConstructor
@Service
public class StorageService {

    private final FileStorageProperties fileStorageProperties;
    private final Logger logger = Logger.getLogger(StorageService.class);
    private static final int COUNT_FOLDER = 3;
    private static final int LEN_NAME_FILE = 5;
    private static final int IMAGE_WEIGHT = 950;
    private static final int IMAGE_HEIGHT = 600;

    public String loadImage(MultipartFile imageFile) throws IOException {
        return saveImage(imageFile, IMAGE_WEIGHT, IMAGE_HEIGHT);
    }

    public ResponseEntity<byte[]> getImage(HttpServletRequest request) throws IOException {
        String pathToFile = request.getRequestURI().substring(1);
        File file = new File(pathToFile);
        byte[] bytes = Files.readAllBytes(file.toPath());
        return ResponseEntity.ok()
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .contentLength(file.length()).body(bytes);
    }

    public String saveImage(MultipartFile imageFile, int imageWeight, int imageHeight) throws IOException {
        String ext = FilenameUtils.getExtension(imageFile.getOriginalFilename());
        byte[] bytes = imageFile.getBytes();
        String pathThreeFolders = getRandomPath();
        File threeFolders = new File(pathThreeFolders);
        if (!threeFolders.exists()) {
            Files.createDirectories(threeFolders.toPath());
        }
        Path pathFile = Paths.get(threeFolders.getAbsolutePath() + File.separator
                + GenerateHash.getHash(LEN_NAME_FILE) + "." + ext);
        BufferedImage image = ImageConverter.imageResize(bytes, imageWeight, imageHeight);
        Files.write(pathFile, ImageConverter.bufferedImageToBytes(image, ext));
        logger.info("File saved at:" + pathFile);
        return pathFile.toString().substring(pathFile.toString().indexOf(fileStorageProperties.getUploadDir()) - 1);
    }

    private String getRandomPath() {
        StringJoiner stringJoiner = new StringJoiner(File.separator);
        stringJoiner.add(fileStorageProperties.getUploadDir());
        String baseNameFolders = GenerateHash.getHash(LEN_NAME_FILE * COUNT_FOLDER);
        int lenNameFolder = baseNameFolders.length() / COUNT_FOLDER;
        for (int i = 0; i < COUNT_FOLDER; i++) {
            int startPosition = lenNameFolder * i;
            String nameFolder = baseNameFolders.substring(startPosition, startPosition + lenNameFolder);
            stringJoiner.add(nameFolder);
        }
        return stringJoiner.toString();
    }
}
