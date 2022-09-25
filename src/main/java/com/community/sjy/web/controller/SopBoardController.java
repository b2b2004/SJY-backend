package com.community.sjy.web.controller;

import com.community.sjy.web.config.auth.PrincipalDetail;
import com.community.sjy.web.model.*;
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
        System.out.println(studyOrProjectBoard.getRoleType());
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

    @GetMapping("/sopBoard/MainBoard")
    public ResponseEntity<?> MainBoard(@PageableDefault(size = 6, sort = "date", direction = Sort.Direction.DESC) Pageable pageable){
        return new ResponseEntity<>(sopBoardService.최신게시판(pageable),HttpStatus.OK);
    }

    @GetMapping("/sopBoard/PopularBoard")
    public ResponseEntity<?> PopularBoard(@PageableDefault(size = 3, sort = "hit", direction = Sort.Direction.DESC) Pageable pageable){
        return new ResponseEntity<>(sopBoardService.최신게시판(pageable),HttpStatus.OK);
    }

    @PostMapping("/sopBoard/OneBoard")
    public ResponseEntity<?> findById(@RequestBody Long id) {
        return new ResponseEntity<>(sopBoardService.한건가져오기(id), HttpStatus.OK); // 200
    }

    @PostMapping("/sopBoard/NoticeWrite")
    public ResponseEntity<?> manageNoticeWrite(@RequestBody SopNotice sopNotice){
        System.out.println("==============================================");
        System.out.println("sopNotice = " + sopNotice);
        System.out.println("==============================================");
        return new ResponseEntity<>(sopBoardService.공지사항저장하기(sopNotice), HttpStatus.OK);
    }

    @GetMapping("/sopBoard/ManageNotice/{sopBoardNoticeId}")
    public ResponseEntity<?> manageNotice(@PathVariable Long sopBoardNoticeId){
        System.out.println("sopBoardNoticeId!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(sopBoardNoticeId);
        return new ResponseEntity<>(sopBoardService.공지사항가져오기(sopBoardNoticeId), HttpStatus.OK);
    }

    @PostMapping("/sopBoard/ManagerWrite")
    public ResponseEntity<?> managerWrite(@RequestBody SopManageBoard sopManageBoard){
        System.out.println("==============================================");
        System.out.println("sopManageBoard = " + sopManageBoard);
        System.out.println("==============================================");
        return new ResponseEntity<>(sopBoardService.추가사항저장하기(sopManageBoard), HttpStatus.OK);
    }

    @GetMapping("/sopBoard/aaa/{id}")
    public ResponseEntity<?> manageSet(@PathVariable Long id){
        System.out.println("sopBoardId!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(id);
        return new ResponseEntity<>(sopBoardService.추가사항가져오기(id), HttpStatus.OK);
    }

    @PutMapping("/sopBoard/ManagerUpdate/{sopBoardId}")
    public ResponseEntity<?> managerUpdate(@PathVariable Long sopBoardId, @RequestBody SopManageBoard sopManageBoard) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(sopManageBoard);
        return new ResponseEntity<>(sopBoardService.추가사항수정하기(sopBoardId, sopManageBoard), HttpStatus.OK);
    }

    @GetMapping("/sopBoard/ManageNoticeDetail/{sopBoardNoticeId}")
    public ResponseEntity<?> manageNoticeDetail(@PathVariable Long sopBoardNoticeId){
        return new ResponseEntity<>(sopBoardService.공지사항한건가져오기(sopBoardNoticeId), HttpStatus.OK);
    }

    @GetMapping("/sopBoard/MainTech/{tech}")
    public ResponseEntity<?> MainTech(@PathVariable String tech){
        System.out.println(tech);
        return new ResponseEntity<>(sopBoardService.테크이미지설정(tech), HttpStatus.OK);
    }

    @PostMapping("/sopBoard/recruitMsg")
    public ResponseEntity<?> sendMsg(@RequestBody RecruitMsg rm){
        return new ResponseEntity<>(sopBoardService.맴버신청(rm), HttpStatus.OK);
    }

    @GetMapping("/sopBoard/recruitMsg/{id}")
    public ResponseEntity<?> findByMsg(@PathVariable int id) {
        return new ResponseEntity<>(sopBoardService.지원자가져오기(id), HttpStatus.OK); // 200
    }

    @PostMapping("/sopBoard/recruitApprove")
    public ResponseEntity<?> setMember(@RequestBody RecruitMsg rm)
    {
        return new ResponseEntity<>(sopBoardService.맴버승인(rm), HttpStatus.OK);
    }

    @PostMapping("/sopBoard/recruitMemberCheck/{username}")
    public ResponseEntity<?> CheckRecruit(@PathVariable String username , @RequestBody Long id){
        System.out.println(username);
        System.out.println("맴버확인@@@@@@@@@@@@@@@@@@@@@@@@@");
        return new ResponseEntity<>(sopBoardService.맴버확인(username , id), HttpStatus.OK);
    }

    @PostMapping("/sopBoard/deleteMember/{username}")
    public ResponseEntity<?> deleteMember(@PathVariable String username , @RequestBody Long id){
        System.out.println(username);
        System.out.println(id);
        System.out.println("맴버확인@@@@@@@@@@@@@@@@@@@@@@@@@");
        return new ResponseEntity<>(sopBoardService.맴버제외(username,id), HttpStatus.OK);
    }


}