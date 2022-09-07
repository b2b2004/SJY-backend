package com.community.sjy.web.repository;

import com.community.sjy.web.model.Board;
import com.community.sjy.web.model.StudyOrProjectBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SopBoardRepository extends  JpaRepository<StudyOrProjectBoard, Long>{

    List<StudyOrProjectBoard>  findByTechStackLike(@Param("Tech") String Tech);
}

