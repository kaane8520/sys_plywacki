package com.my_app.sys_plywacki.service;

import com.my_app.sys_plywacki.model.Referee;
import com.my_app.sys_plywacki.repository.RefereeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RefereeServiceImp implements RefereeService{
    @Autowired
    private RefereeRepository repository;
    @Autowired
    JdbcTemplate template;

    @Override
    public void save(Referee referee) {
        repository.save(referee);
    }
//    @Override
//    public void saveReferee(Referee referee) {
//        repository.saveReferee(referee);
//    }

//    @Override
//    public List<Referee> findAll() {
//        List<Referee> result = (List<Referee>) repository.findAll();
//        if(result.size() > 0) {
//            return result;
//        } else {
//            return new ArrayList<Referee>();
//        }
//    }
}
