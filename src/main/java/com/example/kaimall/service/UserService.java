package com.example.kaimall.service;

import org.springframework.stereotype.Service;

@Service
public interface UserService {

    String register(String loginName, String password);

    String login(String loginName, String password);

    boolean updateUserInfo(String password, Long id);

    boolean logout(Long id);

}
