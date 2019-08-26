package com.example.springbootinterceptor.dao;

import com.example.springbootinterceptor.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Integer> {


    @Query("select u from User u where u.username = :#{#user.username} and u.password = :#{#user.password}")
    User findByUsernameAndPassword(@Param("user") User user);

    User findByUsername(String username);

}
