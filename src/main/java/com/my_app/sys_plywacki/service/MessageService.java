package com.my_app.sys_plywacki.service;

import com.my_app.sys_plywacki.model.Message;

import java.util.List;

public interface MessageService {
    List<Message> findByPersonId(Long idPerson);
}
