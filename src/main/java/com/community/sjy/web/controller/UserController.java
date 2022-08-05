package com.community.sjy.web.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.community.sjy.web.config.auth.PrincipalDetail;
import com.community.sjy.web.dto.ResponseDto;
import com.community.sjy.web.model.*;
import com.community.sjy.web.service.BoardService;
import com.community.sjy.web.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.UUID;


@Controller
@RestController
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @GetMapping("/joinForm")
    public String joinForm() {
        return "/user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "/user/loginForm";
    }

    @GetMapping("/profile")
    public ResponseEntity<?> user(Authentication authentication) {
        PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();
        System.out.println("principal/profile = " + principal);
        return new ResponseEntity<>(principal, HttpStatus.OK);
    }

    @DeleteMapping("/profile/delete")
    public ResponseEntity<?> deleteById(Authentication authentication) {
        PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();
        System.out.println("principal/delete  = " + principal);
        return new ResponseEntity<>(userService.삭제하기(principal.getUser().getId()), HttpStatus.OK); // 200
    }

    @PutMapping("/profile/changeNickname/{username}")
    public ResponseEntity<?> chage(Authentication authentication,@PathVariable String username) {
        PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();
        int id = principal.getUser().getId();
        System.out.println("id!!!!!!!!!!!!!!!!!!!!"+id);
        System.out.println("username!!!!!!!!!!!!!!!!!!!!"+username);
        return new ResponseEntity<>(userService.닉네임수정하기(id, username), HttpStatus.OK);
    }

    @PostMapping("/profile/chagePassword")
    public ResponseEntity<?> chagePassword(@RequestBody CheckPw user, Authentication authentication) {
        PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();

        System.out.println("Password 정보 확인 = " + user.password + " " + user.password1);
        System.out.println("principalPassword = " + principal.getUser().getPassword());

        return new ResponseEntity<String>(userService.수정하기(user, principal.getUser().getPassword(), principal.getUser().getId()), HttpStatus.OK);
    }

    @GetMapping("/profile/board")
    public ResponseEntity<?> findByUsername(Authentication authentication) {
        PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();
        String username = principal.getUser().getUsername();
        System.out.println("username!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! =" + username);
        return new ResponseEntity<>(boardService.프로필게시판가져오기(username), HttpStatus.OK); // 200
    }

    @GetMapping("/profile/sopboard/{username}")
    public ResponseEntity findBysopboardUser(@PathVariable String username) {
        System.out.println("떠야한다 =" + username);
        return new ResponseEntity(userService.회원찾기(username), HttpStatus.OK); // 200
    }

    @GetMapping("/manager")
    public ResponseEntity<?> findbyAllUsername() {
        return new ResponseEntity<>(userService.모두가져오기(), HttpStatus.OK);
    }

    @DeleteMapping("/manager/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        System.out.println("manager id = " + id);
        return new ResponseEntity<>(userService.삭제하기(id), HttpStatus.OK);
    }
}