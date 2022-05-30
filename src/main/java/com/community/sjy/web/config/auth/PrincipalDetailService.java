package com.community.sjy.web.config.auth;

import com.community.sjy.web.model.User;
import com.community.sjy.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // bean 등록
public class PrincipalDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // 스프링이 로그인 요청을 가로챌 때 , username password 변수를 가로채는데
    // password 부분 처리는 알아서함
    // username이 DB에 있는지만 확인해서 리턴해주면 됨
    // 이거 안만들어주면  username 이 그냥 user로 들어감 강제로 넣어줘야댐

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("PrincipalDetailService 동작 확인");

        User user = userRepository.findByUsername(username);
        if(user == null) {
            return null;
        }else {
            return new PrincipalDetail(user);
        }

//        User principal = userRepository.findByUsername(username)
//                .orElseThrow(() ->{
//                   return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다."+username);
//                });
//        System.out.println("확인 " +principal);
//        return new PrincipalDetail(principal); // 시큐리티 세션에 유저 정보가 저장 됨 -> 타입이 userdetail 타입
    }
}
