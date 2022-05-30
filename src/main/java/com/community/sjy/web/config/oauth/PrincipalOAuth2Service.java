package com.community.sjy.web.config.oauth;

import com.community.sjy.web.config.auth.PrincipalDetail;
import com.community.sjy.web.config.oauth.provider.GoogleUserInfo;
import com.community.sjy.web.config.oauth.provider.KakaoUserInfo;
import com.community.sjy.web.config.oauth.provider.NaverUserInfo;
import com.community.sjy.web.config.oauth.provider.OAuth2UserInfo;
import com.community.sjy.web.model.RoleType;
import com.community.sjy.web.model.User;
import com.community.sjy.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class PrincipalOAuth2Service extends DefaultOAuth2UserService{

    @Enumerated(EnumType.STRING)
    private RoleType role;


    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 구글로그인 버튼 클릭 -> 구글 로그인창 -> 로그인 완료 -> code 리턴
        // AccessToken 요청 --> 회원프로필 받아야함(loadUser함수)

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("getAttributes" + oAuth2User.getAttributes());


        OAuth2UserInfo oAuth2UserInfo = null;

        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            System.out.println("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }
        else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            System.out.println("네이버 로그인 요청");
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
        }
        else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")){
            System.out.println("카카오 로그인 요청");

           // oAuth2UserInfo = new KakaoUserInfo((Map)oAuth2User.getAttributes().get("kakao_account"));
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        }else {
            System.out.println("구글 네이버 카카오만 지원");
        }

        System.out.println("providerID =" + oAuth2UserInfo.getProviderId());
        System.out.println("email =" + oAuth2UserInfo.getEmail());

        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
        // String provider = userRequest.getClientRegistration().getRegistrationId(); // google
        // String providerId = oAuth2User.getAttribute("sub");
        // String email = oAuth2User.getAttribute("email");
        String username = provider+"_"+providerId; // google_123123123123123
        String password = "SJY";
        RoleType role = RoleType.USER;

        User userEntity = userRepository.findByUsername(username);
        if(userEntity == null){

            userEntity = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(userEntity);
        }else
        {
            System.out.println("이미 회원가입 되어있습니다.");
        }

        return new PrincipalDetail(userEntity,oAuth2User.getAttributes());
    }
}