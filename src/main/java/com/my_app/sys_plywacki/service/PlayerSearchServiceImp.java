package com.my_app.sys_plywacki.service;

import com.my_app.sys_plywacki.model.Player;
import com.my_app.sys_plywacki.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PlayerSearchServiceImp {
    @Autowired
    private PlayerRepository repository;
    public List<Player> listAll(String keyword){
        if(keyword != null){
            return repository.search(keyword);
        }
        return repository.findAll();
    }

}
