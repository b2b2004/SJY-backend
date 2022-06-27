package com.community.sjy.web.controller;

import com.community.sjy.web.config.auth.PrincipalDetail;
import com.community.sjy.web.model.StudyOrProjectBoard;
import com.community.sjy.web.service.SopBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
public class SopBoardController {
    private final SopBoardService sopBoardService;

    @PostMapping("/sopBoard/sopWrite")
    public ResponseEntity<?> sopWrite(@RequestBody StudyOrProjectBoard studyOrProjectBoard, Authentication authentication){
        PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();
        studyOrProjectBoard.setDate(LocalDateTime.now());
        studyOrProjectBoard.setUsername(principal.getUser().getUsername());
        System.out.println("==============================================");
        System.out.println(studyOrProjectBoard.getTechStack());
        System.out.println("==============================================");
        System.out.println(authentication);
        return new ResponseEntity<>(sopBoardService.저장하기(studyOrProjectBoard), HttpStatus.OK);
    }

    @GetMapping("/sopBoard/AllBoard")
    public ResponseEntity<?> AllBoard(){
        return new ResponseEntity<>(sopBoardService.가져오기(), HttpStatus.OK);
    }
    // @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    @GetMapping("/sopBoard/NewBoard")
    public ResponseEntity<?> NewBoard(@PageableDefault(size = 3, sort = "date", direction = Sort.Direction.DESC) Pageable pageable){
        return new ResponseEntity<>(sopBoardService.최신게시판(pageable),HttpStatus.OK);
    }
    @GetMapping("/sopBoard/PopularBoard")
    public ResponseEntity<?> PopularBoard(@PageableDefault(size = 3, sort = "hit", direction = Sort.Direction.DESC) Pageable pageable){
        return new ResponseEntity<>(sopBoardService.최신게시판(pageable),HttpStatus.OK);
    }

//    @GetMapping("/sopBoard/{id}")
//    public ResponseEntity<?> findById(@PathVariable Long id) {
//
//        return new ResponseEntity<>(sopBoardService.한건가져오기(id), HttpStatus.OK); // 200
//    }

    @PostMapping("/sopBoard/{id}")
    public ResponseEntity<?> findById(@RequestBody Long id) {
        return new ResponseEntity<>(sopBoardService.한건가져오기(id), HttpStatus.OK); // 200
    }

}
