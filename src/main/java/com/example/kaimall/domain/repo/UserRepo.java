package com.example.kaimall.domain.repo;

import com.example.kaimall.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {

    User save(User user);

    User findUserByLoginName(String loginName);

    User findUserByLoginNameAndPassword(String loginName, String password);

    User findUserById(Long id);
}
