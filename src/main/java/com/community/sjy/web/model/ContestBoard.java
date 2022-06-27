package com.community.sjy.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ContestBoard {
    @Id // PK를 해당 변수로 하겠다는 뜻.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 해당 데이터베이스 번호증가 전략을 따라가겠다.
    private Long id;

    @Column
    private String username;

    @Column
    private String host;

    @Column
    private String supervision;

    @Column
    private String sponsor;

    @Column
    private String homepage;

    @Column
    private int prize;

    @Column
    private  String image;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String Duration_start;

    @Column
    private String Duration_end;

    @Column
    private int hit;

    @Column
    private LocalDateTime date;


}
