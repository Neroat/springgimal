package com.example.exam_201930202.controller;

import com.example.exam_201930202.config.security.JwtTokenProvider;
import com.example.exam_201930202.dto.board.BoardDto;
import com.example.exam_201930202.dto.board.BoardResponseDto;
import com.example.exam_201930202.dto.board.ChangeBoardDto;
import com.example.exam_201930202.service.board.BoardService;
import com.example.exam_201930202.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public BoardController(BoardService boardService, UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.boardService = boardService;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Operation(summary = "게시글 수정 게시글ID 필요 - 본인만 -")
    @PutMapping()
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<BoardResponseDto> updateBoard(HttpServletRequest request, @RequestBody ChangeBoardDto changeBoardDto) throws Exception {

        String loginUserId = jwtTokenProvider.getUsername(request.getHeader("X-AUTH-TOKEN"));

        BoardResponseDto boardResponseDto = boardService.updateBoard
                (changeBoardDto.getId(), changeBoardDto.getTitle(), changeBoardDto.getContents(), loginUserId);
        return ResponseEntity.status(HttpStatus.OK).body(boardResponseDto);
    }

    @Operation(summary = "게시글 등록 -USER or ADMIN- 권한이 있어야 등록 가능")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping()
    public ResponseEntity<BoardResponseDto> createBoard(HttpServletRequest request, @RequestBody BoardDto boardDto) {

        String loginUserId = jwtTokenProvider.getUsername(request.getHeader("X-AUTH-TOKEN"));
        String loginUsername = userService.getUsernameByuId(loginUserId);

        BoardResponseDto boardResponseDto = boardService.saveBoard(boardDto, loginUserId, loginUsername);
        return ResponseEntity.status(HttpStatus.OK).body(boardResponseDto);
    }

    @Operation(summary = "게시글 삭제 -본인이 작성한 글만 삭제 가능-")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @DeleteMapping()
    public ResponseEntity<String> deleteBoard(HttpServletRequest request, @RequestParam Long id) throws Exception {
        String loginUserId = jwtTokenProvider.getUsername(request.getHeader("X-AUTH-TOKEN"));
        boardService.deleteBoard(id, loginUserId);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }

    @Operation(summary = "게시판 리스트 - 누구나 -")
    @GetMapping("/list")
    public ResponseEntity<List<BoardResponseDto>> listBoard() {
        List<BoardResponseDto> boardResponseDtoList = boardService.getListBoard();
        return ResponseEntity.status(HttpStatus.OK).body(boardResponseDtoList);
    }

    @Operation(summary = "게시글 리스트 (중복가능) - 작성일지 순 (내림차순) - 누구나 -")
    @GetMapping("/listOrderByCreatedAt")
    public ResponseEntity<List<BoardResponseDto>> listBoardOrderByCreatedAt() {
        List<BoardResponseDto> boardResponseDtoList = boardService.getListBoardOrderByCreatedAtDesc();
        return ResponseEntity.status(HttpStatus.OK).body(boardResponseDtoList);
    }

    @Operation(summary = "게시글 정보 (게시글 ID 필요) - 누구나 -")
    @GetMapping()
    public ResponseEntity<List<BoardResponseDto>> selectBoardByUserId(@RequestParam String userId) {
        List<BoardResponseDto> boardResponseDtoList = boardService.getListBoardByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(boardResponseDtoList);
    }
}
