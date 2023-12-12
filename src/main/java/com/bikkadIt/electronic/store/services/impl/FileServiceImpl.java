package com.bikkadIt.electronic.store.services.impl;

import com.bikkadIt.electronic.store.exceptions.BadApiRequestException;
import com.bikkadIt.electronic.store.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);


    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {
        logger.info("Entering into upload image file ");
        String originalFileName = file.getOriginalFilename();
        logger.info("Filename: {}", originalFileName);
        String fileName = UUID.randomUUID().toString();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileNameWithExtension = fileName + extension;
        String fullPathWithFileName = path + fileNameWithExtension;

        logger.info("Filename: {}", fullPathWithFileName);
        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {
            File folder = new File(path);

            if (!folder.exists()) {
                folder.mkdirs();
            }

            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            logger.info("Completed upload image file");
            return fileNameWithExtension;
        } else {
            throw new BadApiRequestException("File with this " + extension + " not allowed!");
        }

    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        logger.info("Entering into get resource  method");
        String fullPath = path  + name ;
        InputStream inputStream = new FileInputStream(fullPath);
        logger.info("Completed  get resource  method");
        return inputStream;
    }
}
