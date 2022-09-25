package com.community.sjy.web.controller;

import com.community.sjy.web.model.Comment;
import com.community.sjy.web.service.BoardService;
import com.community.sjy.web.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<?> save(@RequestBody Comment comment, Authentication authentication){
        comment.setUsername(authentication.getName());
        comment.setBoard_date(LocalDateTime.now());
        return new ResponseEntity<>(commentService.저장하기(comment), HttpStatus.CREATED);
    }


    @GetMapping("/comment/{boardid}")
    public ResponseEntity<?> findById(@PathVariable Long boardid) {
        return new ResponseEntity<>(commentService.댓글가져오기(boardid), HttpStatus.OK); // 200
    }
}
