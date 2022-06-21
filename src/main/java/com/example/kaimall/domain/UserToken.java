package com.example.kaimall.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class UserToken {

    @Id
    private Long userId;

    private String token;

    private Date updataTime;

    private Date expireTime;

}
