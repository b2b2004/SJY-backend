package com.community.sjy.web.model;


import lombok.Data;

@Data
public class KakaoProfile {

    public long id;
    public String connected_at;
    public Properties properties;
    public Kakao_account kakao_account;

    @Data
    static public class Kakao_account {

        public boolean profile_nickname_needs_agreement;
        public boolean profile_image_needs_agreement;
        public Profile profile;
        public boolean has_email;
        public boolean email_needs_agreement;
        public boolean is_email_valid;
        public boolean is_email_verified;
        public String email;

    }

    @Data
    static public class Profile {

        public String nickname;
        public String thumbnail_image_url;
        public String profile_image_url;
        public boolean is_default_image;

    }

    @Data
    static public class Properties {

        public String nickname;
        public String profile_image;
        public String thumbnail_image;

    }
}
