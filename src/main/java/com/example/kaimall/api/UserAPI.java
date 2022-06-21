package com.example.kaimall.api;

import com.example.kaimall.api.VO.UserVO;
import com.example.kaimall.api.param.UserLoginParam;
import com.example.kaimall.api.param.UserRegisterParam;
import com.example.kaimall.common.Constants;
import com.example.kaimall.common.ServiceResultEnum;
import com.example.kaimall.config.annotation.TokenToUser;
import com.example.kaimall.domain.User;
import com.example.kaimall.service.UserService;
import com.example.kaimall.util.NumberUtil;
import com.example.kaimall.util.Result;
import com.example.kaimall.util.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class UserAPI {

    private ResultGenerator resultGenerator;

    private NumberUtil numberUtil;

    private UserService userService;

    @Autowired
    public UserAPI(ResultGenerator resultGenerator, NumberUtil numberUtil, UserService userService) {
        this.resultGenerator = resultGenerator;
        this.numberUtil = numberUtil;
        this.userService = userService;
    }



    @PostMapping("/user/register")
    public Result register(@RequestBody @Valid UserRegisterParam param){
        if (numberUtil.isNotPhoneNumber(param.getLoginName())){
            return resultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_IS_NOT_PHONE.getResult());
        }
        String registerResult = userService.register(param.getLoginName(), param.getPassword());

        log.info("register api,loginName={},loginResult={}", param.getLoginName(), registerResult);

        if (ServiceResultEnum.SUCCESS.getResult().equals(registerResult)){
            return resultGenerator.genSuccessResult();
        }
        return resultGenerator.genFailResult(registerResult);
    }

    @PostMapping("/user/login")
    public Result login(@RequestBody @Valid UserLoginParam param){
        if (numberUtil.isNotPhoneNumber(param.getLoginName())){
            return resultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_ERROR.getResult());
        }
        String loginResult = userService.login(param.getLoginName(), param.getPassword());

        log.info("login api,loginName={},loginResult={}", param.getLoginName(), loginResult);

        //登录成功
        if (!StringUtils.isEmpty(loginResult) && loginResult.length() == Constants.TOKEN_LENGTH) {
            Result result = resultGenerator.genSuccessResult();
            result.setData(loginResult);
            return result;
        }
        //登录失败
        return resultGenerator.genFailResult(loginResult);
    }

    @GetMapping("/user/info")
    public Result<UserVO> getUserDetail(@TokenToUser User user) {
        //已登录则直接返回
        UserVO mallUserVO = new UserVO();
        BeanUtils.copyProperties(user, mallUserVO);
        return resultGenerator.genSuccessResult(mallUserVO);
    }

}
