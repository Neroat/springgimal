package com.example.exam_201930202.controller;

import com.example.exam_201930202.dto.user.UserResponseDto;
import com.example.exam_201930202.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "사용자 리스트 - ADMIN만 가능 -")
    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserResponseDto>> listUser() {
        List<UserResponseDto> userResponseDtoList = userService.listUser();
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDtoList);
    }

    @Operation(summary = "사용자 리스트 (이름순 - 오름차순) - ADMIN만 가능-")
    @GetMapping("/listOrderByName")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserResponseDto>> listUserOrderByNameAsc() {
        List<UserResponseDto> userResponseDtoList = userService.listUserOrderByNameAsc();
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDtoList);
    }
}
