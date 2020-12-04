package com.my_app.sys_plywacki.service;

import com.my_app.sys_plywacki.model.FileStorageException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {

    @Value("${app.upload.dir:${user.home}}")
    public String uploadDir;

    public void uploadFile(MultipartFile file, String fileName) {

        try {
            Path copyLocation = Paths.get(uploadDir + File.separator + StringUtils.cleanPath(fileName));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileStorageException("Nie można zapisać pliku " + file.getOriginalFilename() + ". Spróbuj jeszcze raz!");
        }
    }

}
