package com.example.springbootinterceptor;

import com.example.springbootinterceptor.dao.UserRepository;
import com.example.springbootinterceptor.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserReTest {
    @Autowired
    UserRepository userRepository;
    
    @Test
    public void addUser(){
        User user = new User();
        user.setId(1);
        user.setUsername("zhangwei");
        user.setPassword("abc-123");
        userRepository.save(user);
    }

    @Test
    public void findUser(){
        Optional<User> byId = userRepository.findById(1);
        User user = byId.get();
        System.out.println(user);
    }

//    @Test
//    public void findByUsernameAndPassword(){
//        User user = new User(1,"zhangwei","abc-1234");
//        User byUsernameAndPassword = userRepository.findByUsernameAndPassword(user);
//        System.out.println(byUsernameAndPassword);
//    }
    @Test
    public void findByUsername(){
        User root = userRepository.findByUsername("root");
        System.out.println(root);
    }
}
