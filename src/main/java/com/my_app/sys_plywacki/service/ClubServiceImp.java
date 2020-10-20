package com.my_app.sys_plywacki.service;

import com.my_app.sys_plywacki.model.Club;
import com.my_app.sys_plywacki.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ClubServiceImp {
    @Autowired
    private ClubRepository repository;
//    public List<Club> listAll(String keyword){
//        if(keyword != null){
//            return repository.search(keyword);
//        }
//        return repository.findAll();
//    }
    public void save (Club club){
        repository.save(club);
    }

    public Club get(long id){
        return repository.findById(id).get();
    }

    public void delete(long id){
        repository.deleteById(id);
    }

}
