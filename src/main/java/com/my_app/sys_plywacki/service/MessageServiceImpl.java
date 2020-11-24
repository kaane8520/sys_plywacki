package com.my_app.sys_plywacki.service;

import com.my_app.sys_plywacki.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.my_app.sys_plywacki.repository.MessageRepository;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{
    @Autowired
    MessageRepository messageRepository;
    @Override
    public List<Message> findByPersonId(Long idPerson) {
        return messageRepository.findByIdPerson(idPerson);
    }
}
