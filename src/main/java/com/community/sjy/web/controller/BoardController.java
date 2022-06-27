package com.community.sjy.web.controller;

import com.community.sjy.web.config.auth.PrincipalDetail;
import com.community.sjy.web.model.Board;
import com.community.sjy.web.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/board")
    public ResponseEntity<?> save(@RequestBody Board board, Authentication authentication) {
        System.out.println("==============================board");
        System.out.println(board);
        System.out.println("authentication.getName() = " + authentication.getName());
        board.setUsername(authentication.getName());
        board.setBoard_date(LocalDateTime.now());
        return new ResponseEntity<>(boardService.저장하기(board), HttpStatus.CREATED); // 200
    }

    @GetMapping("/board")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(boardService.모두가져오기(), HttpStatus.OK); // 200
    }

    @GetMapping("/board/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return new ResponseEntity<>(boardService.한건가져오기(id), HttpStatus.OK); // 200
    }

    @PutMapping("/board/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Board board) {
        return new ResponseEntity<>(boardService.수정하기(id, board), HttpStatus.OK); // 200
    }

    @DeleteMapping("/board/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(boardService.삭제하기(id), HttpStatus.OK); // 200
    }

    // 등록 시간


}