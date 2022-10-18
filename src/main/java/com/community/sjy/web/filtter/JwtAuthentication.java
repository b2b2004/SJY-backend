package com.community.sjy.web.filtter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.community.sjy.web.config.auth.PrincipalDetail;
import com.community.sjy.web.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.imageio.IIOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

@RequiredArgsConstructor
public class JwtAuthentication extends UsernamePasswordAuthenticationFilter {

private final AuthenticationManager authenticationManager;

    // login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try{
            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            //authentication 객체가 session 영역에 저장을 해야하고 그 방법이 return 해 주는것
            return authentication;

        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    //attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행됨
    //JWT 토큰을 만들어서 request 요청한 사용자에게 JWT 토큰을 response 해주면 됨.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        PrincipalDetail principalDetail = (PrincipalDetail) authResult.getPrincipal();
        //HMAC Hash암호 방식
        String jwtToken = JWT.create()
                .withSubject(principalDetail.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+(60000*10))) //만료시간 10분
                .withClaim("username", principalDetail.getUsername())
                .withClaim("password", principalDetail.getPassword())
                .sign(Algorithm.HMAC512("SJY"));
          response.setHeader("Authorization", "Bearer "+jwtToken);
          response.setContentType("application/json; charset=utf-8");
    }
}
