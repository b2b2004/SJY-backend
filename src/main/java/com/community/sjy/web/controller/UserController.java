package com.community.sjy.web.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.community.sjy.web.config.auth.PrincipalDetail;
import com.community.sjy.web.dto.ResponseDto;
import com.community.sjy.web.model.CheckPw;
import com.community.sjy.web.model.KakaoProfile;
import com.community.sjy.web.model.OAuthToken;
import com.community.sjy.web.model.User;
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
    public String joinForm(){
    return "/user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "/user/loginForm";
    }

    @GetMapping("/profile")
    public ResponseEntity<?> user(Authentication authentication){
        PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();
        System.out.println("principal/profile = "+principal);
        return new ResponseEntity<>(principal, HttpStatus.OK);
    }

    @DeleteMapping("/profile/delete")
    public ResponseEntity<?> deleteById(Authentication authentication) {
        PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();
        System.out.println("principal/delete  = "+principal);
        return new ResponseEntity<>(userService.삭제하기(principal.getUser().getId()), HttpStatus.OK); // 200
    }

    @PostMapping("/profile/chagePassword")
    public ResponseEntity<?> chagePassword(@RequestBody CheckPw user, Authentication authentication){
        PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();

        System.out.println("Password 정보 확인 = " + user.password + " " + user.password1);
        System.out.println("principalPassword = " + principal.getUser().getPassword());

        return new ResponseEntity<String>(userService.수정하기(user, principal.getUser().getPassword(), principal.getUser().getId()) , HttpStatus.OK);
    }

    @GetMapping("/profile/board")
    public ResponseEntity<?> findByUsername(Authentication authentication) {
        PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();
        String username = principal.getUser().getUsername();
        System.out.println("username!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! =" + username);
        return new ResponseEntity<>(boardService.프로필게시판가져오기(username), HttpStatus.OK); // 200
    }

    @GetMapping("/manager")
    public ResponseEntity<?> findbyAllUsername(){
        return new ResponseEntity<>(userService.모두가져오기(), HttpStatus.OK);
    }

    @DeleteMapping("/manager/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id){
        System.out.println("manager id = "+ id);
        return new ResponseEntity<>(userService.삭제하기(id), HttpStatus.OK);
    }

/*
    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(@RequestParam String code){
        System.out.println("code = ");
        System.out.println(code);


               RestTemplate rt = new RestTemplate();

        System.out.println("code = " + code);
        // Http 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");


        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id","1850ead48a3679266e0c4815936bb150");
        params.add("redirect_uri","http://localhost:8000/auth/kakao/callback");
        params.add("code",code);


      System.out.println("111111111111111111111111");
        System.out.println("param" + params);
        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        // Http 요청하기 - Post방식으로 - 그리고 변수의 응답을 받음
        ResponseEntity<String> response = rt.exchange(
               "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        System.out.println("22222222222222222222222222");
       System.out.println("response" + response);

       // Gson , Json Simple , ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


       System.out.println("33333333333333333333333333");
        System.out.println("oauthToken" + oauthToken);

       System.out.println("카카오 엑세스 토큰" +oauthToken.getAccess_token());

        return code;



        */



       /*
        RestTemplate rt2 = new RestTemplate();
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer "+ oauthToken.getAccess_token());
       headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

      HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers2);


        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
       );
       System.out.println("response2"+response2);
        */

    }

 //   @GetMapping("/auth/kakao/callback" )
  //  public @ResponseBody String kakaoCallback(String code) { // Data를 리턴해주는 컨트롤러 함수
//
//        RestTemplate rt = new RestTemplate();
//
//        System.out.println("code = " + code);
//        // Http 오브젝트 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//
//        // HttpBody 오브젝트 생성
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("grant_type","authorization_code");
//        params.add("client_id","1850ead48a3679266e0c4815936bb150");
//        params.add("redirect_uri","http://localhost:8000/auth/kakao/callback");
//        params.add("code",code);
//
//
//        System.out.println("111111111111111111111111");
//        System.out.println("param" + params);
//        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
//        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
//                new HttpEntity<>(params, headers);
//
//        // Http 요청하기 - Post방식으로 - 그리고 변수의 응답을 받음
//        ResponseEntity<String> response = rt.exchange(
//               "https://kauth.kakao.com/oauth/token",
//                HttpMethod.POST,
//                kakaoTokenRequest,
//                String.class
//        );
//
//
//        System.out.println("22222222222222222222222222");
//        System.out.println("response" + response);
//
//        // Gson , Json Simple , ObjectMapper
//        ObjectMapper objectMapper = new ObjectMapper();
//        OAuthToken oauthToken = null;
//        try {
//            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//
//        System.out.println("33333333333333333333333333");
//        System.out.println("oauthToken" + oauthToken);
//
//        System.out.println("카카오 엑세스 토큰" +oauthToken.getAccess_token());
//
//        RestTemplate rt2 = new RestTemplate();
//        HttpHeaders headers2 = new HttpHeaders();
//        headers2.add("Authorization", "Bearer "+ oauthToken.getAccess_token());
//        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
//                new HttpEntity<>(headers2);
//
//
//        ResponseEntity<String> response2 = rt2.exchange(
//                "https://kapi.kakao.com/v2/user/me",
//                HttpMethod.POST,
//                kakaoProfileRequest,
//                String.class
//        );
//
//        ObjectMapper objectMapper2 = new ObjectMapper();
//        KakaoProfile kakaoProfile = null;
//        try {
//            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("카카오 아이디 ID =" + kakaoProfile.getId());
//
//
//        System.out.println("블로그 유저네임 =" + kakaoProfile.getKakao_account().getEmail() +"_"+ kakaoProfile.getId());
//        System.out.println("블로그 이메일 "+ kakaoProfile.getKakao_account().getEmail());
//        UUID garbagePassword = UUID.randomUUID();
//        System.out.printf("블로그 패스워드" + garbagePassword);
//
//
//        User kakaouser = User.builder()
//                .username(kakaoProfile.getKakao_account().getEmail() +"_"+ kakaoProfile.getId())
//                .password(garbagePassword.toString())
//                .email(kakaoProfile.getKakao_account().getEmail())
//                .build();
//
//
//
//
//        User originUser = userService.회원찾기(kakaouser.getUsername());
//
//
//        if(originUser == null)
//        {
//            userService.회원가입(kakaouser);
//        }
//
//        String jwtToken = JWT.create()
//                .withSubject(originUser.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis()+(60000*10))) //만료시간 10분
//                .withClaim("username", originUser.getUsername())
//                .withClaim("password", originUser.getPassword())
//                .sign(Algorithm.HMAC512("SJY"));
//
//        System.out.println("Kakao jwtToken"+jwtToken);
//
//
//        return jwtToken;
//    }

//}
