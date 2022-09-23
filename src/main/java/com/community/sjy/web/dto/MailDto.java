package com.community.sjy.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class MailDto {
    private String address;
    private String title;
    private String message;
}
