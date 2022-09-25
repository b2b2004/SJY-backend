package com.community.sjy.web.controller;


import com.community.sjy.web.config.auth.PrincipalDetail;
import com.community.sjy.web.model.ContestBoard;
import com.community.sjy.web.service.ContestBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.time.LocalDateTime;


@RequiredArgsConstructor
@RestController
public class ContestBoardController {
    private final ContestBoardService contestBoardService;

    @PostMapping("/contestBoard/contestBoardWrite")
    public ResponseEntity<?> sopWrite(@RequestBody ContestBoard contestBoard, Authentication authentication){

        PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();
        contestBoard.setDate(LocalDateTime.now());
        contestBoard.setUsername(principal.getUser().getUsername());
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
    public ResponseEntity<?> deleteById(@PathVariable Long id,@RequestBody String contestimage) {
        String UPLOAD_PATH = "C:\\workspace\\springbootwork\\SJY\\SJY-frontend\\src\\image\\ContestImage"; // 공모전 이미지 업로드 위치
        String fileId = contestimage;
        File fileDelete = new File(UPLOAD_PATH, fileId);
        if(fileDelete.exists()) { // 파일이 존재하면 삭제
            fileDelete.delete();
        }
        return new ResponseEntity<>(contestBoardService.삭제하기(id), HttpStatus.OK); // 200
    }

    @GetMapping("/contestBoard/NewBoard")
    public ResponseEntity<?> NewBoard(@PageableDefault(size = 9, sort = "date", direction = Sort.Direction.DESC) Pageable pageable){
        return new ResponseEntity<>(contestBoardService.최신게시판(pageable),HttpStatus.OK);
    }
    @GetMapping("/contestBoard/PopularBoard")
    public ResponseEntity<?> PopularBoard(@PageableDefault(size = 9, sort = "hit", direction = Sort.Direction.DESC) Pageable pageable){
        return new ResponseEntity<>(contestBoardService.최신게시판(pageable),HttpStatus.OK);
    }

}

