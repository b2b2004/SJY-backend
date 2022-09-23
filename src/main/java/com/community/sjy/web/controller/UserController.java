package com.community.sjy.web.controller;

import com.community.sjy.web.config.auth.PrincipalDetail;
import com.community.sjy.web.dto.MailDto;
import com.community.sjy.web.model.*;
import com.community.sjy.web.service.BoardService;
import com.community.sjy.web.service.MailService;
import com.community.sjy.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RestController
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private MailService mailService;

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

    @PostMapping("/check/emailandid")
    public ResponseEntity<?> findEmailAndId(@RequestBody User user) {
        return new ResponseEntity<>(userService.이메일아이디확인(user.getUsername(), user.getEmail()), HttpStatus.OK);
    }

    @PostMapping("/check/sendMail")
    public void sendMail(@RequestBody User user) {
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println(user.getUsername());
        System.out.println(user.getEmail());
        MailDto dto = mailService.createMailAndChangePassword(user.getUsername(), user.getEmail());
        mailService.mailSend(dto);
    }
}