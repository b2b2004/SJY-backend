package com.community.sjy.web.service;

import com.community.sjy.web.model.User;
import com.community.sjy.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;


// 스프링이 컴포넌트 스캔을 통해서 Bean 에 넣어줌 DI 가능하게 해줌
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void 회원가입(User user){
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User 로그인(User user) {
        try {
            if(user.getId() == 0)
            {
            }
            return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("UserService : 로그인(): " + e.getMessage());
        }
        return user;
    }
}

/*
    @Transactional(readOnly = true)
    public User 로그인(User user) {
        try {
            return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("UserService : 로그인(): " + e.getMessage());
        }
        return user;
    }
*/