package com.my_app.sys_plywacki.repository;

import com.my_app.sys_plywacki.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import com.my_app.sys_plywacki.model.Message;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{
    @Nullable
    List<Message> findByIdPerson(Long idPerson);
}