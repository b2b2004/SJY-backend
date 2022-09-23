package com.community.sjy.web.service;

import com.community.sjy.web.config.auth.PrincipalDetailService;
import com.community.sjy.web.model.*;
import com.community.sjy.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


// 스프링이 컴포넌트 스캔을 통해서 Bean 에 넣어줌 DI 가능하게 해줌
@Service
public class UserService {

    public BCryptPasswordEncoder encodePWD(){

        return new BCryptPasswordEncoder();
    }


    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private PrincipalDetailService principalDetailService;

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public User 회원찾기(String username){
        User user = userRepository.findByUsername(username);
        System.out.println(user);
        return user;
    }


    @Transactional
    public void 회원가입(User user) {
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setRole(RoleType.USER);
        userRepository.save(user);
    }

    @Transactional
    public String 삭제하기(Integer id) {
        userRepository.deleteById(id);
        return "ok";
    }

    @Transactional
    public String 수정하기(CheckPw user, String principalpw, int id){

        String password = user.password;
        String rawPassword = user.password1;
        String encPassword = encoder.encode(rawPassword);

        System.out.println("id = " + id);
        if(encoder.matches(password, principalpw)){
            User userEntity = userRepository.findById(id)
                    .orElseThrow(()->new IllegalArgumentException("pw"));
            userEntity.setPassword(encPassword);
        }
        else{
            return "현재 비밀번호가 맞지 않습니다.";
        }
        return "비밀번호 변경 성공";
    }


    @Transactional
    public String 닉네임수정하기(int userId,String username){
        User userEntity = userRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("아이디를 잘못 입력"));
        System.out.println(username);
        userEntity.setUsername(username);
        return "닉네임수정완료";
    }

    @Transactional(readOnly = true)
    public List<User> 모두가져오기(){
        return userRepository.findAll();
    }

    @Transactional
    public User 프로필이미지저장(String username,String imageName){
        User user = userRepository.findByUsername(username);
        System.out.println(user);
        user.setImage(imageName);
        return user;
    }

    @Transactional
    public User 프로필이미지삭제(String username){
        User user = userRepository.findByUsername(username);
        System.out.println(user);
        user.setImage(null);
        return user;
    }

    @Transactional
    public String 이메일아이디확인(String username, String email){
        User user = userRepository.findByUsername(username);
        System.out.println(user);
        if(user == null)
        {
            return "아이디가 존재하지 않습니다.";
        }

        if(user.getEmail().equals(email) == false)
        {
            return "이메일이 존재하지 않습니다.";
        }
        return "유저 확인";
    }
}