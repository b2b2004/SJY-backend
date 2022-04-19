package com.community.sjy.web.repository;

import com.community.sjy.web.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// CRUD 가지고 있음.
// 자동으로 bean등록이 된다.
// @Repository // 생략가능
public interface UserRepository extends JpaRepository<User, Integer> {

    // SELECT *FROM user WHERE username = ?
    User findByUsername(String username);
}


//JPA Naming 쿼리
//SELECT * FROM user WHERE username =? AND password=?;
// User findByUsernameAndPassword(String username, String password);