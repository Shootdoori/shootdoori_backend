package com.shootdoori.match.user.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.shootdoori.match.entity.common.Position;
import com.shootdoori.match.entity.common.SkillLevel;
import com.shootdoori.match.team.domain.value.UniversityName;
import com.shootdoori.match.user.domain.value.Bio;
import com.shootdoori.match.user.domain.value.Department;
import com.shootdoori.match.user.domain.value.Email;
import com.shootdoori.match.user.domain.value.KakaoTalkId;
import com.shootdoori.match.user.domain.value.Password;
import com.shootdoori.match.user.domain.value.StudentYear;
import com.shootdoori.match.user.domain.value.UserName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    @DisplayName("회원 정보 수정 테스트")
    void update() {
        // given
        User user = createUser();

        // when
        user.update(Position.AM, SkillLevel.PRO, Bio.of("변경된 자기소개"));

        // then
        assertThat(user.getPosition()).isEqualTo(Position.AM);
        assertThat(user.getSkillLevel()).isEqualTo(SkillLevel.PRO);
        assertThat(user.getBio().value()).isEqualTo("변경된 자기소개");
    }

    @Test
    @DisplayName("비밀번호 변경 테스트")
    void setPassword() {
        // given
        User user = createUser();
        Password newPassword = Password.of("NewPassword123!");

        // when
        user.setPassword(newPassword);

        // then
        assertThat(user.getPassword()).isEqualTo("NewPassword123!");
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
}
