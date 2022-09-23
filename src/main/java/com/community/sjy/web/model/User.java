package com.community.sjy.web.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@Entity // User 클래스가 MYSQL 테이블이 생성됨.
// @DynamicInsert insert 시에 null 놈 제외시켜준다.
public class User {

    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
    private int id; // 시퀀스 , auto_increment

    @Column(nullable = false, length = 300, unique = true)
    private String username;
    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    // DB는 RoleType 이라는게 없다
    @Enumerated(EnumType.STRING)
    private RoleType role;

    @CreationTimestamp  // 시간이 자동 입력
    private Timestamp createDate;

    private String provider;
    private String providerId;

    @Column
    private  String image;

    @Builder
    public User(int id, String username, String password, String email, RoleType role, Timestamp createDate, String provider, String providerId, String image) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.createDate = createDate;
        this.provider = provider;
        this.providerId = providerId;
        this.image = image;
    }
}
