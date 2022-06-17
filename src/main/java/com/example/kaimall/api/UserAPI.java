package com.example.kaimall.api;

import com.example.kaimall.api.param.UserLoginParam;
import com.example.kaimall.api.param.UserRegisterParam;
import com.example.kaimall.common.ServiceResultEnum;
import com.example.kaimall.service.UserService;
import com.example.kaimall.util.NumberUtil;
import com.example.kaimall.util.Result;
import com.example.kaimall.util.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    @PostMapping("/user/login")
//    public Result login(@RequestBody @Valid UserLoginParam param){
//        if (numberUtil.isNotPhoneNumber(param.getLoginName())){
//            return resultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_ERROR.getResult()));
//        }
//
//
//    }

}
