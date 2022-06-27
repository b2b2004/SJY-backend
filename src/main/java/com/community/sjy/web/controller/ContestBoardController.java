package com.community.sjy.web.controller;

import com.community.sjy.web.config.auth.PrincipalDetail;
import com.community.sjy.web.model.ContestBoard;
import com.community.sjy.web.model.StudyOrProjectBoard;
import com.community.sjy.web.service.ContestBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

@RequiredArgsConstructor
@RestController
public class ContestBoardController {
    private final ContestBoardService contestBoardService;

    @PostMapping("/contestBoard/contestBoardWrite")
    public ResponseEntity<?> sopWrite(@RequestBody ContestBoard contestBoard, Authentication authentication){

        PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();
        contestBoard.setDate(LocalDateTime.now());
        contestBoard.setUsername(principal.getUser().getUsername());
        System.out.println(authentication);
        return new ResponseEntity<>(contestBoardService.저장하기(contestBoard), HttpStatus.OK);
    }

    @GetMapping("/contestBoard/AllBoard")
    public ResponseEntity<?> AllBoard(){
        return new ResponseEntity<>(contestBoardService.가져오기(), HttpStatus.OK);
    }


    @PostMapping("/contestBoard/{id}")
    public ResponseEntity<?> findById(@RequestBody Long id) {
        return new ResponseEntity<>(contestBoardService.한건가져오기(id), HttpStatus.OK); // 200
    }

    @DeleteMapping("/contestBoard/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(contestBoardService.삭제하기(id), HttpStatus.OK); // 200
    }

}

