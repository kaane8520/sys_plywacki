package com.my_app.sys_plywacki.service;

import com.my_app.sys_plywacki.model.Coach;
import com.my_app.sys_plywacki.model.Player;
import com.my_app.sys_plywacki.model.Referee;

import java.util.List;

public interface CoachService {

    void save(Coach coach);

    public List<Coach> findByIdPerson(Long idPerson);
}
