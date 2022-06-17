package com.example.kaimall.service.impl;

import com.example.kaimall.common.ServiceResultEnum;
import com.example.kaimall.domain.User;
import com.example.kaimall.domain.repo.UserRepo;
import com.example.kaimall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public String register(String loginName, String password){
        if (userRepo.findUserByLoginName(loginName) != null){
            return ServiceResultEnum.SAME_LOGIN_NAME_EXIST.getResult();
        }
        User registerUser = new User();
        registerUser.setLoginName(loginName);
        registerUser.setPassword(password);
        if (userRepo.save(registerUser) != null){
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

//    @Override
//    public String login(String loginName, String password){
//        User user = userRepo.findUserByLoginNameAndPassword(loginName, password);
//        if (user == null){
//            return ServiceResultEnum.LOGIN_ERROR.getResult();
//        }
//
//
//    }
}
