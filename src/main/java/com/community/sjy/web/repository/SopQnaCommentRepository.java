package com.community.sjy.web.repository;

import com.community.sjy.web.model.Comment;
import com.community.sjy.web.model.SopQnaComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SopQnaCommentRepository extends JpaRepository<SopQnaComment, Long> {
    List<SopQnaComment> findByboardId(Long id);
}
