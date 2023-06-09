package com.example.exam_201930202.service.board.impl;

import com.example.exam_201930202.dao.board.BoardDAO;
import com.example.exam_201930202.dto.board.BoardDto;
import com.example.exam_201930202.dto.board.BoardResponseDto;
import com.example.exam_201930202.entity.Board;
import com.example.exam_201930202.service.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardDAO boardDAO;

    @Autowired
    public BoardServiceImpl(BoardDAO boardDAO) {
        this.boardDAO = boardDAO;
    }

    @Override
    public BoardResponseDto updateBoard(Long id, String title, String contents, String userId) throws Exception {
        String checkUserId = boardDAO.selectBoard(id).getUserId();
        if(userId.equals(checkUserId)) {
            Board changeBoard = boardDAO.updateBoard(id, title, contents);
            return new BoardResponseDto(changeBoard);
        } else throw new Exception("아이디가 일치하지 않습니다.");
    }

    @Override
    public BoardResponseDto saveBoard(BoardDto boardDto, String userId, String username) {
        Board board = new Board();
        board.setTitle(boardDto.getTitle());
        board.setContents(boardDto.getContents());
        board.setUserId(userId);
        board.setUserName(username);
        board.setCreatedAt(LocalDateTime.now());

        Board saveBoard = boardDAO.insertBoard(board);

        BoardResponseDto boardResponseDto = new BoardResponseDto();
        boardResponseDto.setId(saveBoard.getId());
        boardResponseDto.setTitle(saveBoard.getTitle());
        boardResponseDto.setContents(saveBoard.getContents());
        boardResponseDto.setContents(saveBoard.getContents());
        boardResponseDto.setUserName(saveBoard.getUserName());
        return boardResponseDto;
    }

    @Override
    public void deleteBoard(Long id, String userId) throws Exception {
        String checkUserId = boardDAO.selectBoard(id).getUserId();
        if(userId.equals(checkUserId))
        boardDAO.deleteBoard(id);
        else throw new Exception("아이디가 일치하지 않습니다.");
    }

    @Override
    public List<BoardResponseDto> getListBoard() {
        List<Board> boardList = boardDAO.allBoard();
        List<BoardResponseDto> boardResponseDtoList = boardList.stream().map(board ->
                new BoardResponseDto(board)).collect(Collectors.toList());
        return boardResponseDtoList;
    }

    @Override
    public List<BoardResponseDto> getListBoardOrderByCreatedAtDesc() {
        List<Board> boardList = boardDAO.listBoardOrderByCreatedAtDesc();
        List<BoardResponseDto> boardResponseDtoList = boardList.stream().map(board ->
                new BoardResponseDto(board)).collect(Collectors.toList());
        return boardResponseDtoList;
    }

    @Override
    public List<BoardResponseDto> getListBoardByUserId(String userId) {
        List<Board> boardList = boardDAO.listBoardByUserId(userId);
        List<BoardResponseDto> boardResponseDtoList = boardList.stream().map(board ->
                new BoardResponseDto(board)).collect(Collectors.toList());
        return boardResponseDtoList;
    }

    @Override
    public BoardResponseDto getBoardById(Long id) {
        Board selectBoard = boardDAO.selectBoard(id);
        return new BoardResponseDto(selectBoard);
    }
}
