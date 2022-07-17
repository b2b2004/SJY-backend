package com.community.sjy.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SopManageBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long sopBoardId;

    @Column
    private String leader;

    @Column
    private String member;

    @Column
    private String githubAddress;

    @Column
    private String zoomAddress;

    @Column
    private String kakao_Open_Address;
}
