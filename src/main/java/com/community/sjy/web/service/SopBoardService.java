package com.community.sjy.web.service;

import com.community.sjy.web.model.Board;
import com.community.sjy.web.model.SopNotice;
import com.community.sjy.web.model.StudyOrProjectBoard;
import com.community.sjy.web.repository.SopBoardRepository;
import com.community.sjy.web.repository.SopManageNoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SopBoardService {

    private final SopBoardRepository sopBoardRepository;
    private final SopManageNoticeRepository sopManageNoticeRepository;

    @Transactional
    public StudyOrProjectBoard 저장하기(StudyOrProjectBoard studyOrProjectBoard)
    {return sopBoardRepository.save(studyOrProjectBoard);}

    @Transactional
    public SopNotice 공지사항저장하기(SopNotice sopNotice)
    {return sopManageNoticeRepository.save(sopNotice);}

    @Transactional
    public List<SopNotice> 공지사항가져오기(Long sopBoardNoticeId)
    {System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+sopManageNoticeRepository.findBysopBoardId(sopBoardNoticeId));
        return sopManageNoticeRepository.findBysopBoardId(sopBoardNoticeId);}

    @Transactional
    public SopNotice 공지사항한건가져오기(Long id) {
        SopNotice sopEntity = sopManageNoticeRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("id check"));
        return sopEntity;
    }


    @Transactional
    public List<StudyOrProjectBoard> 가져오기()
    {return sopBoardRepository.findAll();}



    @Transactional
    public Page<StudyOrProjectBoard> 최신게시판(Pageable pageable)
    {return sopBoardRepository.findAll(pageable);}



    @Transactional
    public StudyOrProjectBoard 한건가져오기(Long id) {
        StudyOrProjectBoard sopEntity = sopBoardRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("id check")); // 영속화 (book 오브젝트) -> 영속성 컨텍스트 보관

        int hit = sopEntity.getHit();
        hit++;
        sopEntity.setHit(hit);
        return sopEntity;
    }
}