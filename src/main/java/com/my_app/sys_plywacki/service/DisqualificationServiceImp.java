package com.my_app.sys_plywacki.service;

import com.my_app.sys_plywacki.model.Disqualification;
import com.my_app.sys_plywacki.repository.DisqualificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DisqualificationServiceImp implements DisqualificationService{
    @Autowired
    DisqualificationRepository repository;

    public void addDisqualifications(){
        List<String> disqualificationsList = Arrays.asList("Brak dyskwalifikacji", "O1", "O2",
                "O3", "O4", "O5", "O6", "O7", "O8", "O9", "O10", "O11", "O12");
        Long i = 1L;
        for(String rn: disqualificationsList){
            Disqualification dis = new Disqualification();
            dis.setId(i);
            dis.setDisqualificationname(rn);

            repository.save(dis);
            i++;
        }
    }
}
