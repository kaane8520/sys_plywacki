package com.my_app.sys_plywacki.service;
import com.my_app.sys_plywacki.model.FileDB;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;


public interface FileDBService {
    public FileDB store(MultipartFile file) throws IOException;
    public FileDB getFile(String id);
    public Stream<FileDB> getAllFiles();
}
