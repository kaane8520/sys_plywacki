package com.my_app.sys_plywacki.service;

import com.my_app.sys_plywacki.model.Player;
import com.my_app.sys_plywacki.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerSearchServiceImp implements PlayerSearchService {
    @Autowired
    private PlayerRepository repository;
//    public List<Player> listAll(String keyword){
//        if(keyword != null){
//            return repository.search(keyword);
//        }
//        return repository.findAll();
//    }

}
