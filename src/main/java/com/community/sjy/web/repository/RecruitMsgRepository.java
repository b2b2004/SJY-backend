package com.community.sjy.web.repository;

import com.community.sjy.web.model.RecruitMsg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitMsgRepository extends  JpaRepository<RecruitMsg, Long>{
    List<RecruitMsg> findBysopboardid(int id);
    RecruitMsg findByusername(String username);

}