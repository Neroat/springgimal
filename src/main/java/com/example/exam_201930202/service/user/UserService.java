package com.example.exam_201930202.service.user;

import com.example.exam_201930202.dto.user.UserResponseDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {

    String getUsernameByuId(String userId);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    List<UserResponseDto> listUser();

    List<UserResponseDto> listUserOrderByNameAsc() throws UsernameNotFoundException;
}
