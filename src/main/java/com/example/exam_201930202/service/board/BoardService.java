package com.example.exam_201930202.service.board;

import com.example.exam_201930202.dto.board.BoardDto;
import com.example.exam_201930202.dto.board.BoardResponseDto;

import java.util.List;

public interface BoardService {

    BoardResponseDto updateBoard(Long id, String title, String contents, String userId) throws Exception;

    BoardResponseDto saveBoard(BoardDto boardDto, String userId, String username);

    void deleteBoard(Long id, String userId) throws Exception;

    List<BoardResponseDto> getListBoard();

    List<BoardResponseDto> getListBoardOrderByCreatedAtDesc();

    List<BoardResponseDto> getListBoardByUserId(String userId);

    BoardResponseDto getBoardById(Long id);

}
