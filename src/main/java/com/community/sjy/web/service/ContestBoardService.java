package com.community.sjy.web.service;

import com.community.sjy.web.model.ContestBoard;
import com.community.sjy.web.model.StudyOrProjectBoard;
import com.community.sjy.web.repository.ContestBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ContestBoardService {

    private final ContestBoardRepository contestBoardRepository;

    @Transactional
    public ContestBoard 저장하기(ContestBoard contestBoard){
        return contestBoardRepository.save(contestBoard);
    }

    @Transactional
    public List<ContestBoard> 가져오기()
    {return contestBoardRepository.findAll();}


    @Transactional
    public ContestBoard 한건가져오기(Long id) {

        ContestBoard sopEntity = contestBoardRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("id check")); // 영속화 (book 오브젝트) -> 영속성 컨텍스트 보관

        int hit = sopEntity.getHit();
        hit++;
        sopEntity.setHit(hit);
        return sopEntity;
    }


    @Transactional
    public String 삭제하기(Long id) {
        contestBoardRepository.deleteById(id); // 오류가 터지면 익셉션을 타니까.. 신경쓰지 말고
        return "ok";
    }
}
