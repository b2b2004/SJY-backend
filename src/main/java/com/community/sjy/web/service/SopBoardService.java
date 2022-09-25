package com.community.sjy.web.service;

import com.community.sjy.web.model.*;
import com.community.sjy.web.repository.RecruitMsgRepository;
import com.community.sjy.web.repository.SopBoardRepository;
import com.community.sjy.web.repository.SopManageBoardRepository;
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

    private final RecruitMsgRepository recruitMsgRepository;
    private final SopBoardRepository sopBoardRepository;
    private final SopManageNoticeRepository sopManageNoticeRepository;
    private final SopManageBoardRepository sopManageBoardRepository;

    @Transactional
    public StudyOrProjectBoard 저장하기(StudyOrProjectBoard studyOrProjectBoard)
    {return sopBoardRepository.save(studyOrProjectBoard);}

    @Transactional
    public SopNotice 공지사항저장하기(SopNotice sopNotice)
    {
        return sopManageNoticeRepository.save(sopNotice);
    }

    @Transactional
    public List<SopNotice> 공지사항가져오기(Long sopBoardNoticeId)
    {
        return sopManageNoticeRepository.findBysopBoardId(sopBoardNoticeId);
    }

    @Transactional
    public SopManageBoard 추가사항저장하기(SopManageBoard sopManageBoard)
    {
        return sopManageBoardRepository.save(sopManageBoard);
    }

    @Transactional
    public SopManageBoard 추가사항가져오기(Long id)
    {
        SopManageBoard sopManageBoardEntity = sopManageBoardRepository.findBysopBoardId(id);
        return sopManageBoardEntity;
    }

    @Transactional
    public SopManageBoard 추가사항수정하기(Long sopBoardId, SopManageBoard sopManageBoard) {
        SopManageBoard sopManageboardEntity = sopManageBoardRepository.findBysopBoardId(sopBoardId);
        sopManageboardEntity.setGithubAddress(sopManageBoard.getGithubAddress());
        sopManageboardEntity.setZoomAddress(sopManageBoard.getZoomAddress());
        sopManageboardEntity.setKakaoOpenAddress(sopManageBoard.getKakaoOpenAddress());
        return sopManageboardEntity;
    }


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

    @Transactional
    public List<StudyOrProjectBoard> 테크이미지설정(String techStack)
    {
        return sopBoardRepository.findByTechStackLike("%"+techStack+"%");
    }


    @Transactional
    public RecruitMsg 맴버신청(RecruitMsg rm) {
        String name = rm.getUsername();
        long id = rm.getSopboardid();
        StudyOrProjectBoard sop = sopBoardRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("id check"));
        sop.setMemberchecking(name);
        return recruitMsgRepository.save(rm);
    }

    @Transactional
    public List<RecruitMsg> 지원자가져오기(int id)
    {
        return recruitMsgRepository.findBysopboardid(id);
    }

    @Transactional
    public String 맴버승인(RecruitMsg rm)
    {
        long id = rm.getSopboardid();
        StudyOrProjectBoard sopEntity = sopBoardRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("id check"));

        String member1 = sopEntity.getMemberchecking();
        member1 = member1.replace(rm.getUsername() , "");

        String member = sopEntity.getMember();
        if(member != null)
        {
            sopEntity.setMember(member+","+rm.getUsername());
        }
        else
        {
            sopEntity.setMember(rm.getUsername());
        }

        int cnt = sopEntity.getRecruitment_cnt();
        cnt++;
        sopEntity.setRecruitment_cnt(cnt);
        sopEntity.setMemberchecking(member1);
        recruitMsgRepository.deleteById(rm.getId());

        return "good";
    }

    @Transactional
    public String 맴버거절(RecruitMsg rm)
    {
        long id = rm.getId();
        long sopid = rm.getSopboardid();
        System.out.println("@@@@@@@@@@@@@" + id);
        StudyOrProjectBoard sopEntity = sopBoardRepository.findById(sopid)
                .orElseThrow(()->new IllegalArgumentException("id check"));
        String username = sopEntity.getMemberchecking();
        username = username.replace(username , "");
        sopEntity.setMemberchecking(username);
        recruitMsgRepository.deleteById(id);
        return "bad";
    }



    @Transactional
    public String 맴버확인(String username , Long id)
    {
        StudyOrProjectBoard sop = sopBoardRepository.findById(id).orElseThrow(()->new IllegalArgumentException("id check"));
        String mbcheck = sop.getMemberchecking();

        String member = "";
        if(sop.getMember() !=null)
        {
            member = sop.getMember();
        }

        if(member.contains(username))
        {
            return "Member";
        }
        else if(mbcheck.contains(username))
        {
            return "Membercheck";
        }
        else
        {
            return "NotMember";
        }
    }

    @Transactional
    public String 맴버제외(String username, Long id)
    {
        StudyOrProjectBoard sop = sopBoardRepository.findById(id).orElseThrow(()->new IllegalArgumentException("id check"));

        String member = sop.getMember();
        if(member.equals(username)) //한명만 남았을 경우
        {
            member = null;

        } //나머지 경우
        else
        {
            member = member.replace(username +",", "");
            member = member.replace(","+username , "");
        }

        sop.setMember(member);
        int cnt = sop.getRecruitment_cnt();
        cnt--;
        sop.setRecruitment_cnt(cnt);
            return "Member";
    }
}