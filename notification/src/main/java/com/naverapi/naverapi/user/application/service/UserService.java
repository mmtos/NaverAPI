package com.naverapi.naverapi.user.application.service;

import com.naverapi.naverapi.user.domain.UserRepository;
import com.naverapi.naverapi.user.ui.contorller.dto.UserResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class UserService {
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAllDesc(){
        return userRepository.findAllDesc()
                             .map(UserResponseDto::new)
                             .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponseDto findUserByEmail(String email){
        return UserResponseDto.builder()
                .user(userRepository.findByEmail(email).get())
                .build();
    }
}
