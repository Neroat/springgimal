package com.example.exam_201930202.repository;

import com.example.exam_201930202.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllBoardByOrderByCreatedAtDesc();

    List<Board> findBoardsByUserId(String userId);

    Board findBoardById(Long id);

}
