package com.my_app.sys_plywacki.service;

import com.my_app.sys_plywacki.model.RefereeRoles;
import com.my_app.sys_plywacki.repository.RefereeRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RefereeRolesServiceImp implements RefereeRolesService {
    @Autowired
    RefereeRolesRepository repository;
    @Autowired
    JdbcTemplate template;

    public void addRoles(){
        //jesli w tabeli nie ma ról
//        if(repository.findAll() == null){
        {
            List<String> refereeRoles = Arrays.asList("Sedzia glowny", "Starter", "Kierownik wyscigu",
                    "Kierownik inspektorow nawrotow", "Sedzia mierzacy czas", "Inspektor nawrotow", "Sedzia stylu");
            Long i = 1L;
            for(String rn: refereeRoles){
                RefereeRoles role = new RefereeRoles();
                role.setIdrefereerole(i);
                role.setRefereerolename(rn);
                repository.save(role);
                i++;
            }
        }

    }

    @Override
    public List<RefereeRoles> findAll() {
        List<RefereeRoles> result = (List<RefereeRoles>) repository.findAll();
        if(result.size() > 0) {
            return result;
        } else {
            return new ArrayList<RefereeRoles>();
        }
    }
}
