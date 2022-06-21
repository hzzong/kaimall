package com.example.kaimall.util;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class NumberUtil {

    public boolean isPhoneNumber(String phone) {
        Pattern pattern = Pattern.compile("^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[0-8])|(18[0-9]))\\d{8}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public boolean isNotPhoneNumber(String phone){
        return !isPhoneNumber(phone);
    }

    /**
     * 生成指定长度的随机数
     *
     * @param length
     * @return
     */
    public int genRandomNum(int length) {
        int num = 1;
        double random = Math.random();
        if (random < 0.1) {
            random = random + 0.1;
        }
        for (int i = 0; i < length; i++) {
            num = num * 10;
        }
        return (int) ((random * num));
    }

}
