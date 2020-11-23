package com.my_app.sys_plywacki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.my_app.sys_plywacki.model.FileDB;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {

}