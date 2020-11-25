package com.my_app.sys_plywacki.service;


import com.my_app.sys_plywacki.model.Competition;
import com.my_app.sys_plywacki.repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetitionServiceImp {
    @Autowired
    private CompetitionRepository repository;

    public List<Competition> listAll(String keyword){
        //if(keyword != null){
            //return repository.search(keyword);

        //}
        return repository.findAll();
    }


}
