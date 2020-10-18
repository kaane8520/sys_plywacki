package com.my_app.sys_plywacki.service;

import com.my_app.sys_plywacki.model.Club;
import com.my_app.sys_plywacki.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ClubSearchServiceImp {
    @Autowired
    private ClubRepository repository;
    public List<Club> listAll(String keyword){
        if(keyword != null){
            return repository.search(keyword);
        }
        return repository.findAll();
    }

}
