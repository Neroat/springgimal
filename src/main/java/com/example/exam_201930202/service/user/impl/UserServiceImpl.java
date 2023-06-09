package com.example.exam_201930202.service.user.impl;

import com.example.exam_201930202.dto.user.UserResponseDto;
import com.example.exam_201930202.entity.User;
import com.example.exam_201930202.repository.UserRepository;
import com.example.exam_201930202.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String getUsernameByuId(String userId) {
        return userRepository.getByUid(userId).getUsername();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getByUid(username);
    }

    @Override
    public List<UserResponseDto> listUser() {
        List<User> userList = userRepository.findAll();
        List<UserResponseDto> userResponseDtoList = userList.stream().map(user -> new UserResponseDto(user)).collect(Collectors.toList());
        return userResponseDtoList;
    }

    @Override
    public List<UserResponseDto> listUserOrderByNameAsc() throws UsernameNotFoundException {
        List<User> userList = userRepository.findAll(Sort.by(Sort.Order.asc("name")));
        List<UserResponseDto> userResponseDtoList = userList.stream().map(user -> new UserResponseDto(user)).collect(Collectors.toList());
        return userResponseDtoList;
    }
}
