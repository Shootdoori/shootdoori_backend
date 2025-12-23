package com.shootdoori.match.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.shootdoori.match.entity.common.Position;
import com.shootdoori.match.entity.common.SkillLevel;
import com.shootdoori.match.team.domain.value.UniversityName;
import com.shootdoori.match.user.domain.User;
import com.shootdoori.match.user.domain.value.Bio;
import com.shootdoori.match.user.domain.value.Department;
import com.shootdoori.match.user.domain.value.Email;
import com.shootdoori.match.user.domain.value.KakaoTalkId;
import com.shootdoori.match.user.domain.value.Password;
import com.shootdoori.match.user.domain.value.StudentYear;
import com.shootdoori.match.user.domain.value.UserName;
import com.shootdoori.match.user.dto.ProfileResponse;
import com.shootdoori.match.user.dto.ProfileUpdateRequest;
import com.shootdoori.match.user.mapper.UserMapper;
import com.shootdoori.match.user.repository.UserRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserCommandService 테스트")
class UserCommandServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserQueryService userQueryService;

    private UserCommandService userCommandService;

    @BeforeEach
    void setUp() {
        userCommandService = new UserCommandService(userRepository, passwordEncoder, userMapper,
            userQueryService);
    }

    @Test
    @DisplayName("유저 정보 수정 테스트")
    void update() {
        // given
        Long userId = 1L;
        ProfileUpdateRequest request = new ProfileUpdateRequest("정상수", "아마추어", "FW", "저 좀 칩니다.");
        User user = createUser();
        ProfileResponse response = createProfileResponse();

        given(userQueryService.findByIdForEntity(userId)).willReturn(user);
        given(userMapper.toProfileResponse(any(User.class))).willReturn(response);

        // when
        ProfileResponse result = userCommandService.update(userId, request);

        // then
        assertThat(user.getSkillLevel()).isEqualTo(SkillLevel.AMATEUR);
        assertThat(user.getPosition()).isEqualTo(Position.FW);
        assertThat(user.getBio().value()).isEqualTo("저 좀 칩니다.");
        assertThat(result).isEqualTo(response);
    }

    private User createUser() {
        return new User(
            UserName.of("정상수"),
            Email.of("gamza@kangwon.ac.kr"),
            Password.of("Password123!"),
            Position.FW,
            SkillLevel.AMATEUR,
            KakaoTalkId.of("kakao123"),
            UniversityName.of("강원대학교"),
            Department.of("컴퓨터공학과"),
            StudentYear.of("23"),
            Bio.of("안녕하세요")
        );
    }

    private ProfileResponse createProfileResponse() {
        return new ProfileResponse(
            "정상수", "아마추어", "gamza@kangwon.ac.kr", "kakao123", "FW",
            "강원대학교", "컴퓨터공학과", "23", "안녕하세요", LocalDateTime.now()
        );
    }
}
