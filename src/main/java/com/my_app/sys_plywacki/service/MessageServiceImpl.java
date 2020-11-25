package com.my_app.sys_plywacki.service;

import com.my_app.sys_plywacki.model.Message;
import com.my_app.sys_plywacki.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.my_app.sys_plywacki.repository.MessageRepository;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    PersonService personService;

    @Override
    public List<Message> findByPersonId(Long idPerson) {
        return messageRepository.findByIdPerson(idPerson);
    }
    public void addMessage(){
        String content = "Nie masz zadnych wiadomosci";
        Message message = new Message();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person p = personService.findByUsername(auth.getName());

        message.setContent(content);
        message.setIdPerson(p.getIdPerson());
        messageRepository.save(message);
    }
}
