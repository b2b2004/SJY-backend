package com.community.sjy.web.controller;

import com.community.sjy.web.model.Board;
import com.community.sjy.web.model.RecruitMsg;
import com.community.sjy.web.model.SopQnaBoard;
import com.community.sjy.web.service.BoardService;
import com.community.sjy.web.service.SopQnaBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
@RequiredArgsConstructor
@RestController
public class SopQnaBoardController {

    private final SopQnaBoardService sopQnaBoardService;

    @PostMapping("/sopBoard/qnaboard")
    public ResponseEntity<?> save(@RequestBody SopQnaBoard sopQnaBoard, Authentication authentication) {
        sopQnaBoard.setUsername(authentication.getName());
        sopQnaBoard.setBoard_date(LocalDateTime.now());
        return new ResponseEntity<>(sopQnaBoardService.저장하기(sopQnaBoard), HttpStatus.CREATED); // 200
    }

    @GetMapping("/sopBoard/qnaboard")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(sopQnaBoardService.모두가져오기(), HttpStatus.OK); // 200
    }

    @GetMapping("/sopBoard/qnaboard/{id}")
    public ResponseEntity<?> findByIdAll(@PathVariable Long id) {
        return new ResponseEntity<>(sopQnaBoardService.질문게시판가져오기(id), HttpStatus.OK); // 200
    }

    @GetMapping("/sopBoard/allqnaboard/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return new ResponseEntity<>(sopQnaBoardService.한건가져오기(id), HttpStatus.OK); // 200
    }


    @PutMapping("/sopBoard/qnaboard/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Board board) {
        return new ResponseEntity<>(sopQnaBoardService.수정하기(id, board), HttpStatus.OK); // 200
    }

    @DeleteMapping("/sopBoard/qnaboard/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(sopQnaBoardService.삭제하기(id), HttpStatus.OK); // 200
    }


}