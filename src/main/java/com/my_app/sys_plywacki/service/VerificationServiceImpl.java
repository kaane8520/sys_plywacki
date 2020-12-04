package com.my_app.sys_plywacki.service;

import com.my_app.sys_plywacki.model.Coach;
import com.my_app.sys_plywacki.model.Player;
import com.my_app.sys_plywacki.model.Verification;
import com.my_app.sys_plywacki.repository.PlayerRepository;
import com.my_app.sys_plywacki.repository.VerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VerificationServiceImpl implements VerificationService{
    @Autowired
    private VerificationRepository repository;
    @Autowired
    JdbcTemplate template;

    @Override
    public void save(Verification verification) {
        System.out.println("Zapisuje weryfikacje...");
        repository.save(verification);
    }


    public List<Verification> findAll(){
        List<Verification> result = (List<Verification>) repository.findAll();
        if(result.size() > 0) {
            return result;
        } else {
            return new ArrayList<Verification>();
        }
    }
    public List<Verification> findByIdPerson(Long idPerson){
        return repository.findByIdPerson(idPerson);
    }
}
