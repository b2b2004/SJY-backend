package com.community.sjy.web.config.oauth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.community.sjy.web.config.auth.PrincipalDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class Oauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        System.out.println("실행은 될까? 순서는 ?");
        PrincipalDetail principalDetail = (PrincipalDetail) authentication.getPrincipal();
        String username = principalDetail.getUsername();
        String password = principalDetail.getPassword();

        System.out.println(username);


        System.out.println("==============================================================");
       //HMAC Hash암호 방식
        String jwtToken = JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis()+(60000*10))) //만료시간 10분
               .withClaim("username",username)
               .withClaim("password", password)
                .sign(Algorithm.HMAC512("SJY"));



       System.out.println("jwt 실행 확인");
       System.out.println(jwtToken);
        response.setHeader("Authorization", "Bearer "+jwtToken);
        response.setContentType("application/json; charset=utf-8");
   //     super.onAuthenticationSuccess(request, response, authentication);


        String url = makeRedirectUrl(jwtToken);
        System.out.println("url: " + url);
        getRedirectStrategy().sendRedirect(request, response, url);


    }
    private String makeRedirectUrl(String token) {
        return UriComponentsBuilder.fromUriString("http://localhost:3000/GoogleLoginRedirect/"+token)
                .build().toUriString();
    }


}

