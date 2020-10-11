package com.my_app.sys_plywacki.service;

import com.my_app.sys_plywacki.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}