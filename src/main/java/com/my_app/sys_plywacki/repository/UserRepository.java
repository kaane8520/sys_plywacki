package com.my_app.sys_plywacki.repository;

import com.my_app.sys_plywacki.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}