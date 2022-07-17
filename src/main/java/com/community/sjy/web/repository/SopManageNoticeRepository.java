package com.community.sjy.web.repository;

import com.community.sjy.web.model.SopNotice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SopManageNoticeRepository extends JpaRepository<SopNotice, Long> {
    List<SopNotice> findBysopBoardId(Long id);
}
