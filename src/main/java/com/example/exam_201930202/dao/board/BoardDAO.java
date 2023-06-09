package com.example.exam_201930202.dao.board;

import com.example.exam_201930202.entity.Board;

import java.util.List;

public interface BoardDAO {

    Board updateBoard(Long id, String title, String contents) throws Exception;

    Board insertBoard(Board board);

    void deleteBoard(Long id) throws Exception;

    List<Board> allBoard();

    List<Board> listBoardOrderByCreatedAtDesc();

    List<Board> listBoardByUserId(String userId);

    Board selectBoard(Long id);
}
