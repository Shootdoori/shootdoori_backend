package com.shootdoori.match.user.service;

import com.shootdoori.match.entity.common.Position;
import com.shootdoori.match.entity.common.SkillLevel;
import com.shootdoori.match.user.domain.User;
import com.shootdoori.match.user.dto.ProfileResponse;
import com.shootdoori.match.user.dto.ProfileUpdateRequest;
import com.shootdoori.match.user.dto.UserCreateRequest;
import com.shootdoori.match.user.mapper.UserMapper;
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

@Service
public class UserCommandService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserQueryService userQueryService;

    public UserCommandService(UserRepository userRepository, PasswordEncoder passwordEncoder,
        UserMapper userMapper, UserQueryService userQueryService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.userQueryService = userQueryService;
    }

    public ProfileResponse create(UserCreateRequest createRequest) {

        userQueryService.validateEmailNotDuplicated(createRequest.email());
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
