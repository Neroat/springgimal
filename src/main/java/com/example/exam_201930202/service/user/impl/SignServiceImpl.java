package com.example.exam_201930202.service.user.impl;

import com.example.exam_201930202.config.security.JwtTokenProvider;
import com.example.exam_201930202.dto.sign.CommonResponse;
import com.example.exam_201930202.dto.sign.SignInResultDto;
import com.example.exam_201930202.dto.sign.SignUpResultDto;
import com.example.exam_201930202.entity.User;
import com.example.exam_201930202.repository.UserRepository;
import com.example.exam_201930202.service.user.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SignServiceImpl implements SignService {

    public UserRepository userRepository;
    public JwtTokenProvider jwtTokenProvider;
    public PasswordEncoder passwordEncoder;

    @Autowired
    public SignServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public SignUpResultDto signUp(String id, String password, String name, String email, String role) {
        System.out.println("[signUp] 회원가입");
        User user;
        if (role.equalsIgnoreCase("admin")) {
            user = User.builder().uid(id).password(passwordEncoder.encode(password)).name(name).email(email)
                    .roles(Collections.singletonList("ROLE_ADMIN")).build();
        } else {
            user = User.builder().uid(id).password(passwordEncoder.encode(password)).name(name).email(email)
                    .roles(Collections.singletonList("ROLE_USER")).build();
        }
        User savedUser = userRepository.save(user);
        SignUpResultDto signUpResultDto = new SignUpResultDto();
        if (!savedUser.getName().isEmpty()) {
            setSuccessResult(signUpResultDto);
        } else {
            setFailResult(signUpResultDto);
        }
        return signUpResultDto;
    }

    private void setSuccessResult(SignUpResultDto signUpResultDto) {
        signUpResultDto.setSuccess(true);
        signUpResultDto.setCode(CommonResponse.SUCCESS.getCode());
        signUpResultDto.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    private void setFailResult(SignUpResultDto signUpResultDto) {
        signUpResultDto.setSuccess(false);
        signUpResultDto.setCode(CommonResponse.FAIL.getCode());
        signUpResultDto.setMsg(CommonResponse.FAIL.getMsg());
    }

    @Override
    public SignInResultDto signIn(String id, String password) throws Exception {
        User user = userRepository.getByUid(id);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException();
        }
        String token = jwtTokenProvider.createToken(String.valueOf(user.getUid()), user.getRoles());
        SignInResultDto signInResultDto = SignInResultDto.builder().token(token).build();
        setSuccessResult(signInResultDto);
        return signInResultDto;
    }
}