package com.my_app.sys_plywacki.service;

import com.my_app.sys_plywacki.model.Coach;
import com.my_app.sys_plywacki.model.Verification;

import java.util.List;

public interface VerificationService {

    public void save(Verification verification);

    List<Verification> findAll();
    public List<Verification> findByIdPerson(Long idPerson);
}
