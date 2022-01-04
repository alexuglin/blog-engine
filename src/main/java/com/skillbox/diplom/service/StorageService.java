package com.skillbox.diplom.service;

import com.skillbox.diplom.exceptions.WrongDataException;
import com.skillbox.diplom.exceptions.enums.Errors;
import com.skillbox.diplom.util.GenerateHash;
import com.skillbox.diplom.util.ImageConverter;
import com.skillbox.diplom.util.UtilResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

@RequiredArgsConstructor
@Service
public class StorageService {

    private static final String UPLOAD = "upload";
    @Value("${file.extensions}")
    private String extensions;
    private final Logger logger = Logger.getLogger(StorageService.class);
    private static final String POINT = ".";
    private static final int COUNT_FOLDER = 3;
    private static final int LEN_NAME_FILE = 5;
    private static final int IMAGE_WEIGHT = 950;
    private static final int IMAGE_HEIGHT = 600;

    public String loadImage(MultipartFile imageFile) throws IOException {
        String ext = FilenameUtils.getExtension(imageFile.getOriginalFilename());
        if (Objects.isNull(ext) || !extensions.contains(ext)) {
            throw new WrongDataException(UtilResponse.getErrorResponse(Map.of(Errors.IMAGE.getFieldName(), Errors.FILE_FORMAT.getMessage())));
        }
        return saveImage(imageFile, ext);
    }

    public ResponseEntity<byte[]> getImage(HttpServletRequest request) throws IOException {
        String pathToFile = request.getRequestURI().substring(1);
        File file = new File(pathToFile);
        byte[] bytes = Files.readAllBytes(file.toPath());
        return ResponseEntity.ok()
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .contentLength(file.length()).body(bytes);
    }

    private String saveImage(MultipartFile imageFile, String ext) throws IOException {
        byte[] bytes = imageFile.getBytes();
        String pathThreeFolders = getRandomPath();
        File threeFolders = new File(pathThreeFolders);
        if (!threeFolders.exists()) {
            Files.createDirectories(threeFolders.toPath());
        }
        Path pathFile = Paths.get(threeFolders.getAbsolutePath() + File.separator
                + GenerateHash.getHash(LEN_NAME_FILE) + POINT + ext);
        BufferedImage image = ImageConverter.imageResize(bytes,IMAGE_WEIGHT,IMAGE_HEIGHT);
        Files.write(pathFile, ImageConverter.bufferedImageToBytes(image, ext));
        logger.info("File saved at:" + pathFile);
        return pathFile.toString().substring(pathFile.toString().indexOf(UPLOAD) - 1);
    }

    private String getRandomPath() {
        StringJoiner stringJoiner = new StringJoiner(File.separator);
        stringJoiner.add(UPLOAD);
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
