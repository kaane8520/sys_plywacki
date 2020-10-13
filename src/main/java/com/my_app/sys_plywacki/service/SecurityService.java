package com.my_app.sys_plywacki.service;

public interface SecurityService {
    boolean isAuthenticated();
    void autoLogin(String email, String password);
}