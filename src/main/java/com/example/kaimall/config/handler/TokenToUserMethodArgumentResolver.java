package com.example.kaimall.config.handler;

import com.example.kaimall.common.Constants;
import com.example.kaimall.config.annotation.TokenToUser;
import com.example.kaimall.domain.User;
import com.example.kaimall.domain.UserToken;
import com.example.kaimall.domain.repo.UserRepo;
import com.example.kaimall.domain.repo.UserTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class TokenToUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private UserRepo userRepo;
    private UserTokenRepo userTokenRepo;

    @Autowired
    public TokenToUserMethodArgumentResolver(UserRepo userRepo, UserTokenRepo userTokenRepo) {
        this.userRepo = userRepo;
        this.userTokenRepo = userTokenRepo;
    }



    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(TokenToUser.class)) {
            return true;
        }
        return false;
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String token = webRequest.getHeader("token");
        if (null != token && !"".equals(token) && token.length() == Constants.TOKEN_LENGTH){
            UserToken userToken = userTokenRepo.findUserTokenByToken(token);
            if (userToken == null || userToken.getExpireTime().getTime() <= System.currentTimeMillis()){
                throw new RuntimeException("TOKEN_EXPIRE_ERROR");
            }
            User user = userRepo.findUserById(userToken.getUserId());
            if (user == null){
                throw new RuntimeException(("USER_NULL_ERROR"));
            }
            return user;
        } else {
            throw new RuntimeException("NOT_LOGIN_ERROR");
        }
    }
}
