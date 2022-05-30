package com.community.sjy.web.config.oauth.provider;

import java.util.Map;

public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();
    String getUsername();
    String getPassword();
    String getEmail();
    String getName();
}
