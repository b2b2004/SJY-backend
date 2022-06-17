package com.community.sjy.web.repository;

import com.community.sjy.web.model.Board;
import com.community.sjy.web.model.StudyOrProjectBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SopBoardRepository extends  JpaRepository<StudyOrProjectBoard, Long>{

}

