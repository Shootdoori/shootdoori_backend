package com.shootdoori.match.user.service;

import com.shootdoori.match.user.domain.User;
import com.shootdoori.match.user.mapper.UserMapper;
import com.shootdoori.match.user.repository.UserRepository;
import com.shootdoori.match.user.dto.ProfileResponse;
import com.shootdoori.match.user.domain.value.Email;
import com.shootdoori.match.exception.common.ErrorCode;
import com.shootdoori.match.exception.common.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserQueryService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserQueryService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public ProfileResponse findProfileById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(ErrorCode.PROFILE_NOT_FOUND));
        return userMapper.toProfileResponse(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(Email.of(email))
            .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    public User findByIdForEntity(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(ErrorCode.PROFILE_NOT_FOUND));
    }
}
