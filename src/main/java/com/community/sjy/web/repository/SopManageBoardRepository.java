package com.community.sjy.web.repository;

import com.community.sjy.web.model.SopManageBoard;
import com.community.sjy.web.model.SopNotice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SopManageBoardRepository extends JpaRepository<SopManageBoard,Long> {
    SopManageBoard findBysopBoardId(Long id);

}