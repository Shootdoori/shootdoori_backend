package com.shootdoori.match.user.service;

import com.shootdoori.match.entity.common.Position;
import com.shootdoori.match.entity.common.SkillLevel;
import com.shootdoori.match.user.domain.User;
import com.shootdoori.match.user.dto.ProfileResponse;
import com.shootdoori.match.user.dto.ProfileUpdateRequest;
import com.shootdoori.match.user.dto.UserCreateRequest;
import com.shootdoori.match.user.mapper.UserMapper;
import com.shootdoori.match.user.repository.RefreshTokenRepository;
import com.shootdoori.match.user.repository.UserRepository;
import com.shootdoori.match.user.domain.value.Bio;
import com.shootdoori.match.user.domain.value.Department;
import com.shootdoori.match.user.domain.value.Email;
import com.shootdoori.match.user.domain.value.KakaoTalkId;
import com.shootdoori.match.user.domain.value.Password;
import com.shootdoori.match.user.domain.value.StudentYear;
import com.shootdoori.match.user.domain.value.UserName;
import com.shootdoori.match.team.domain.value.UniversityName;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserCommandService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserQueryService userQueryService;
    private final RefreshTokenRepository refreshTokenRepository;

    public UserCommandService(UserRepository userRepository, PasswordEncoder passwordEncoder,
        UserMapper userMapper, UserQueryService userQueryService,
        RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.userQueryService = userQueryService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public ProfileResponse create(UserCreateRequest createRequest) {

        userQueryService.validateEmailNotDuplicated(createRequest.email());
        Password.validateRaw(createRequest.password());

        User user = new User(
            new UserName(createRequest.name()),
            new Email(createRequest.email()),
            new Password(passwordEncoder.encode(createRequest.password())),
            Position.fromCode(createRequest.position()),
            SkillLevel.fromDisplayName(createRequest.skillLevel()),
            new KakaoTalkId(createRequest.kakaoTalkId()),
            new UniversityName(createRequest.university()),
            new Department(createRequest.department()),
            new StudentYear(createRequest.studentYear()),
            new Bio(createRequest.bio())
        );

        User saveUser = userRepository.save(user);
        return userMapper.toProfileResponse(saveUser);
    }

    public ProfileResponse update(Long id, ProfileUpdateRequest updateRequest) {
        User user = userQueryService.findByIdForEntity(id);

        user.update(
            Position.fromCode(updateRequest.position()),
            SkillLevel.fromDisplayName(updateRequest.skillLevel()),
            new Bio(updateRequest.bio())
        );

        return userMapper.toProfileResponse(user);
    }

    public void delete(Long id) {
        User user = userQueryService.findByIdForEntity(id);
        refreshTokenRepository.deleteByUser(user);
        userRepository.delete(user);
    }
}
