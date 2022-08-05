package com.community.sjy.web.controller;

import com.community.sjy.web.model.Comment;
import com.community.sjy.web.model.SopQnaComment;
import com.community.sjy.web.service.CommentService;
import com.community.sjy.web.service.SopQnaCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
public class SopQnaCommentController {
    private final SopQnaCommentService sopQnaCommentService;

    @PostMapping("/sopQnaComment")
    public ResponseEntity<?> save(@RequestBody SopQnaComment sopQnaComment, Authentication authentication){
        sopQnaComment.setUsername(authentication.getName());
        sopQnaComment.setBoard_date(LocalDateTime.now());
        System.out.println("comment = " +sopQnaComment);
        return new ResponseEntity<>(sopQnaCommentService.저장하기(sopQnaComment), HttpStatus.CREATED);
    }


    @GetMapping("/sopQnaComment/{boardid}")
    public ResponseEntity<?> findById(@PathVariable Long boardid) {
        System.out.println("boardid = " + boardid);
        return new ResponseEntity<>(sopQnaCommentService.댓글가져오기(boardid), HttpStatus.OK); // 200
    }
}
