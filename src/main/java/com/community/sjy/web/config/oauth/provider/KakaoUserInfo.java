package com.community.sjy.web.config.oauth.provider;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo{

    private Map<String, Object> attributes;

    public KakaoUserInfo(Map<String, Object> attributes){
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getUsername() {

        return (String) attributes.get("username");
    }

    @Override
    public String getPassword() {
        return (String) attributes.get("id");
    }

    @Override
    public String getEmail() {
        return (String) getKakaoAccount().get("email");
    }

    public Map<Object, Object> getKakaoAccount() {
        return(Map<Object, Object>) attributes.get("kakao_account");
    }

    @Override
    public String getName() {
        return (String) attributes.get("nickname");
    }
}
