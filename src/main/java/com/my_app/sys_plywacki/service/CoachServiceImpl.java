package com.my_app.sys_plywacki.service;

import com.my_app.sys_plywacki.model.Coach;
import com.my_app.sys_plywacki.repository.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CoachServiceImpl implements CoachService {

    @Autowired
    private CoachRepository repository;
    @Autowired
    JdbcTemplate template;

    @Override
    public void save(Coach coach) {
        repository.save(coach);
    }
}
