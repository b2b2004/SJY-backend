package com.community.sjy.web.service;

import com.community.sjy.web.model.Comment;
import com.community.sjy.web.model.SopQnaComment;
import com.community.sjy.web.repository.CommentRepository;
import com.community.sjy.web.repository.SopQnaCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SopQnaCommentService {


    private final SopQnaCommentRepository sopQnaCommentRepository;

    @Transactional
    public String 삭제하기(Long id) {
        sopQnaCommentRepository.deleteById(id); // 오류가 터지면 익셉션을 타니까.. 신경쓰지 말고
        return "ok";
    }
    @Transactional // 서비스 함수가 종료될 때 commit할지 rollback할지 트랜잭션 관리하겠다.
    public SopQnaComment 저장하기(SopQnaComment sopQnaComment) {
        return sopQnaCommentRepository.save(sopQnaComment);
    }

    @Transactional(readOnly = true)
    public List<SopQnaComment> 모두가져오기(){
        return sopQnaCommentRepository.findAll();
    }

    public List<SopQnaComment> 댓글가져오기(Long boardid) {
        return sopQnaCommentRepository.findByboardId(boardid);
    }

}
