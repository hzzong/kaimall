package com.example.kaimall.domain.repo;

import com.example.kaimall.domain.UserToken;
import org.springframework.data.repository.CrudRepository;

public interface UserTokenRepo extends CrudRepository<UserToken, Long> {

    UserToken findUserTokenById(Long id);

    UserToken save(UserToken userToken);

    UserToken findUserTokenByToken(String token);


}
