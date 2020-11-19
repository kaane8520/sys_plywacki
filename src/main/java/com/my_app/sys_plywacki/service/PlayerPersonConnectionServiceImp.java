package com.my_app.sys_plywacki.service;


import com.my_app.sys_plywacki.model.PlayerPersonConnection;
import com.my_app.sys_plywacki.repository.PlayerPersonConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class PlayerPersonConnectionServiceImp implements PlayerPersonConnectionService {
    @Autowired
    PlayerPersonConnectionRepository repository;
    public List<PlayerPersonConnection> findAll(){
        List<PlayerPersonConnection> result = (List<PlayerPersonConnection>) repository.findAll();
        if(result.size() > 0) {
            return result;
        } else {
            return new ArrayList<PlayerPersonConnection>();
        }
    }
}
