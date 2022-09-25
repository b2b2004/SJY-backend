package com.community.sjy.web.service;

import java.util.List;

import com.community.sjy.web.model.Board;
import com.community.sjy.web.repository.BoardRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import lombok.RequiredArgsConstructor;

// 기능을 정의할 수 있고, 트랜잭션을 관리할 수 있음.

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional // 서비스 함수가 종료될 때 commit할지 rollback할지 트랜잭션 관리하겠다.
    public Board 저장하기(Board board) {
        return boardRepository.save(board);
    }

    @Transactional(readOnly = true) // JPA 변경감지라는 내부 기능 활성화 X, update시의 정합성을 유지해줌. insert의 유령데이터현상(팬텀현상) 못막음.
    public Board 한건가져오기(Long id) {

        return boardRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("id를 확인해주세요!!"));
    }

    @Transactional(readOnly = true)
    public List<Board> 프로필게시판가져오기(String username) {
        return boardRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public List<Board> 모두가져오기(){
        return boardRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
    }

    @Transactional
    public Board 수정하기(Long id, Board board) {
        // 더티체팅 update치기
        Board boardEntity = boardRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("id를 확인해주세요!!")); // 영속화 (book 오브젝트) -> 영속성 컨텍스트 보관
        boardEntity.setTitle(board.getTitle());
        boardEntity.setContent(board.getContent());
        return boardEntity;
    } // 함수 종료 => 트랜잭션 종료 => 영속화 되어있는 데이터를 DB로 갱신(flush) => commit    ======> 더티체킹

    @Transactional
    public String 삭제하기(Long id) {
        boardRepository.deleteById(id); // 오류가 터지면 익셉션을 타니까.. 신경쓰지 말고
        return "ok";
    }
}


