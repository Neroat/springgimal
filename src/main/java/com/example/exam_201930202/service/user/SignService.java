package com.example.exam_201930202.service.user;

import com.example.exam_201930202.dto.sign.SignInResultDto;
import com.example.exam_201930202.dto.sign.SignUpResultDto;

public interface SignService {
    SignUpResultDto signUp(String id, String password, String name, String email, String role);
    SignInResultDto signIn(String id, String password) throws Exception;
}
