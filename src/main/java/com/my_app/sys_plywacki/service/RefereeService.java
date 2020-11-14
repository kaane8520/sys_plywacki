package com.my_app.sys_plywacki.service;

import com.my_app.sys_plywacki.model.Referee;
import com.my_app.sys_plywacki.repository.RefereeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public interface RefereeService {
//    public void saveReferee(Referee referee);
    void save(Referee referee);
//    List<Referee> findAll();




}
