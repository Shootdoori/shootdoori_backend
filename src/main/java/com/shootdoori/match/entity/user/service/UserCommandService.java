package com.shootdoori.match.entity.user.service;

import com.shootdoori.match.entity.common.Position;
import com.shootdoori.match.entity.common.SkillLevel;
import com.shootdoori.match.entity.user.User;
import com.shootdoori.match.entity.user.UserValidator;
import com.shootdoori.match.entity.user.dto.ProfileResponse;
import com.shootdoori.match.entity.user.dto.ProfileUpdateRequest;
import com.shootdoori.match.entity.user.dto.UserCreateRequest;
import com.shootdoori.match.entity.user.mapper.UserMapper;
import com.shootdoori.match.entity.user.repository.UserRepository;
import com.shootdoori.match.entity.user.value.Bio;
import com.shootdoori.match.entity.user.value.Department;
import com.shootdoori.match.entity.user.value.Email;
import com.shootdoori.match.entity.user.value.KakaoTalkId;
import com.shootdoori.match.entity.user.value.Password;
import com.shootdoori.match.entity.user.value.StudentYear;
import com.shootdoori.match.entity.user.value.UserName;
import com.shootdoori.match.team.domain.value.UniversityName;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserCommandService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserValidator userValidator;
    private final UserQueryService userQueryService;

    public UserCommandService(UserRepository userRepository, PasswordEncoder passwordEncoder,
        UserMapper userMapper, UserValidator userValidator, UserQueryService userQueryService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.userValidator = userValidator;
        this.userQueryService = userQueryService;
    }

    public ProfileResponse create(UserCreateRequest createRequest) {

        userValidator.validateEmailNotDuplicated(createRequest.email());
        Password.validateRaw(createRequest.password());

        User user = new User(
            UserName.of(createRequest.name()),
            Email.of(createRequest.email()),
            Password.of(passwordEncoder.encode(createRequest.password())),
            Position.fromCode(createRequest.position()),
            SkillLevel.fromDisplayName(createRequest.skillLevel()),
            KakaoTalkId.of(createRequest.kakaoTalkId()),
            UniversityName.of(createRequest.university()),
            Department.of(createRequest.department()),
            StudentYear.of(createRequest.studentYear()),
            Bio.of(createRequest.bio())
        );

        User saveUser = userRepository.save(user);
        return userMapper.toProfileResponse(saveUser);
    }

    public ProfileResponse findById(Long userId) {
        return userQueryService.findProfileById(userId);
    }

    public ProfileResponse update(Long id, ProfileUpdateRequest updateRequest) {
        User user = userQueryService.findByIdForEntity(id);

        user.update(
            Position.valueOf(updateRequest.position()),
            SkillLevel.valueOf(updateRequest.skillLevel()),
            Bio.of(updateRequest.bio())
        );

        return userMapper.toProfileResponse(user);
    }

    public void delete(Long id) {
        User user = userQueryService.findByIdForEntity(id);
        userRepository.delete(user);
    }
}
