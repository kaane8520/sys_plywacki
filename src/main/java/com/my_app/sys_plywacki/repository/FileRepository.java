package com.my_app.sys_plywacki.repository;

import com.my_app.sys_plywacki.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
    public File findByName(String filename);
}
