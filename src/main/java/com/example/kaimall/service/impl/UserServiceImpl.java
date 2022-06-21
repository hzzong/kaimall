package com.example.kaimall.service.impl;

import com.example.kaimall.common.ServiceResultEnum;
import com.example.kaimall.domain.User;
import com.example.kaimall.domain.UserToken;
import com.example.kaimall.domain.repo.UserRepo;
import com.example.kaimall.domain.repo.UserTokenRepo;
import com.example.kaimall.service.UserService;
import com.example.kaimall.util.NumberUtil;
import com.example.kaimall.util.SystemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;
    private UserTokenRepo userTokenRepo;
    private SystemUtil systemUtil;
    private NumberUtil numberUtil;


    @Autowired
    public UserServiceImpl(UserRepo userRepo, UserTokenRepo userTokenRepo, SystemUtil systemUtil, NumberUtil numberUtil) {
        this.userRepo = userRepo;
        this.userTokenRepo = userTokenRepo;
        this.systemUtil = systemUtil;
        this.numberUtil = numberUtil;
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

    @Override
    public String login(String loginName, String password){
        User user = userRepo.findUserByLoginNameAndPassword(loginName, password);
        if (user == null){
            return ServiceResultEnum.LOGIN_ERROR.getResult();
        }
        String token = getNewToken(System.currentTimeMillis() + "", user.getId());

        UserToken userToken = userTokenRepo.findUserTokenByUserId(user.getId());

        Date now = new Date();
        Date expireTime = new Date(now.getTime() + 2 * 24 * 3600 * 1000);
        if (userToken == null){
            userToken = new UserToken();
            userToken.setUserId(user.getId());
        }
        userToken.setToken(token);
        userToken.setUpdataTime(now);
        userToken.setExpireTime(expireTime);
        userTokenRepo.save(userToken);
        return token;
    }

    /**
     * 获取token值
     *
     * @param timeStr
     * @param userId
     * @return
     */
    private String getNewToken(String timeStr, Long userId) {
        String src = timeStr + userId + numberUtil.genRandomNum(4);
        return systemUtil.genToken(src);
    }
}
