package com.example.exam_201930202.repository;

import com.example.exam_201930202.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User getByUid(String uid);
    List<User> findAll(Sort sort);
}
