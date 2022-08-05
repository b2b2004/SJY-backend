package com.community.sjy.web.repository;

import com.community.sjy.web.model.SopQnaBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// @Repository 적어야 스프링 IoC에 빈으로 등록이 되는데...!!
// JpaRepository를 extends하면 생략가능함.
// JpaRepository는 CRUD 함수를 들고 있음.
public interface SopQnaBoardRepository extends JpaRepository<SopQnaBoard, Long>{
    // SELECT *FROM board WHERE username = ?
    List<SopQnaBoard> findByUsername(String username);

    List<SopQnaBoard> findBysopboardId(Long id);
}