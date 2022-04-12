package com.community.sjy.web.controller.api;

import com.community.sjy.web.dto.ResponseDto;
import com.community.sjy.web.model.RoleType;
import com.community.sjy.web.model.User;
import com.community.sjy.web.repository.UserRepository;
import com.community.sjy.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

    @CrossOrigin
    @PostMapping("/api/user")
    public ResponseDto<Integer> save(@RequestBody User user){
        System.out.println("API 호출 확인");
        user.setRole(RoleType.USER);
        userService.회원가입(user);

        return new ResponseDto<Integer>(HttpStatus.OK.value(),1); // 자바오브젝트를 Json으로 바꿔서 리턴
    }


    @CrossOrigin
    @PostMapping("/api/login")
    public ResponseEntity<Integer> login(@RequestBody User user, HttpSession session){
        System.out.println("Login API 호출 확인");
        User principal = userService.로그인(user);
        if(principal != null){
            session.setAttribute("principal",principal);
            return new ResponseEntity<Integer>(HttpStatus.OK);
        } else
        {
            return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
