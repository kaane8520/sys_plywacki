package com.my_app.sys_plywacki.service;

import com.my_app.sys_plywacki.model.Club;
import com.my_app.sys_plywacki.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.swing.tree.RowMapper;
import javax.swing.tree.TreePath;
import javax.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ClubServiceImp {
    @Autowired
    private ClubRepository repository;
    @Autowired
    JdbcTemplate template;


    public List<Club> findAll(){
        List<Club> result = (List<Club>) repository.findAll();
        if(result.size() > 0) {
            return result;
        } else {
            return new ArrayList<Club>();
        }
    }




//    public void save (Club club){
//        repository.save(club);
//    }
//
//    public Club get(long id){
//        return repository.findById(id).get();
//    }
//
//    public void delete(long id){
//        repository.deleteById(id);
//    }

}
