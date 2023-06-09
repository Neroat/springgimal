package com.example.exam_201930202.dao.board.impl;

import com.example.exam_201930202.dao.board.BoardDAO;
import com.example.exam_201930202.entity.Board;
import com.example.exam_201930202.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class BoardDAOImpl implements BoardDAO {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardDAOImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public Board updateBoard(Long id, String title, String contents) throws Exception {
        Optional<Board> selectedBoard = boardRepository.findById(id);

        Board updatedBoard;
        if(selectedBoard.isPresent()) {
            Board board = selectedBoard.get();
            board.setTitle(title);
            board.setContents(contents);
            board.setUpdatedAt(LocalDateTime.now());

            updatedBoard = boardRepository.save(board);
        } else throw new Exception();

        return updatedBoard;
    }

    @Override
    public Board insertBoard(Board board) {
        Board saveBoard = boardRepository.save(board);
        return saveBoard;
    }

    @Override
    public void deleteBoard(Long id) throws Exception {
        Optional<Board> selectedBoard = boardRepository.findById(id);

        if(selectedBoard.isPresent()) {
            Board board = selectedBoard.get();
            boardRepository.delete(board);
        } else throw new Exception();
    }

    @Override
    public List<Board> allBoard() {
        return boardRepository.findAll();
    }

    @Override
    public List<Board> listBoardOrderByCreatedAtDesc() {
        return boardRepository.findAllBoardByOrderByCreatedAtDesc();
    }

    @Override
    public List<Board> listBoardByUserId(String userId) {
        return boardRepository.findBoardsByUserId(userId);
    }

    @Override
    public Board selectBoard(Long id) {
        return boardRepository.findBoardById(id);
    }
}
