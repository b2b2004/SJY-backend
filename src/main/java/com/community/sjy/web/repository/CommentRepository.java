package com.community.sjy.web.repository;

import com.community.sjy.web.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository  extends JpaRepository<Comment, Long> {
    List<Comment> findByboardId(Long id);
}
