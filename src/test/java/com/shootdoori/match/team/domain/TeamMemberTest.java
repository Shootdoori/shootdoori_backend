package com.shootdoori.match.team.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.shootdoori.match.exception.common.NoPermissionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("TeamMember 도메인 테스트")
class TeamMemberTest {

    private Long loginUserId = 1L;
    private Long targetUserId = 2L;
    private Long basicUserId = 3L;

    private Team team;

    private TeamMember loginMember;
    private TeamMember targetMember;
    private TeamMember basicMember;

    @BeforeEach
    void setUp() {
        team = Team.of("강원대 FC", "강원대학교", TeamType.CENTRAL_CLUB, "설");
        loginMember = TeamMember.of(team, loginUserId, TeamMemberRole.LEADER.getDisplayName());
        targetMember = TeamMember.of(team, targetUserId, TeamMemberRole.MEMBER.getDisplayName());
        basicMember = TeamMember.of(team, basicUserId,
            TeamMemberRole.MEMBER.getDisplayName());
    }

    @Test
    @DisplayName("changeRole - 멤버 역할 변경 성공 테스트")
    void changeRole_Success() {
        // when
        loginMember.changeRole("부회장");

        // then
        assertThat(loginMember.getRole().getDisplayName()).isEqualTo("부회장");
    }

    @Test
    @DisplayName("changeRole - 존재하지 않는 역할으로 변경 시 예외 발생 테스트")
    void changeRole_InvalidRole_Exception() {
        // when & then
        assertThatThrownBy(() -> loginMember.changeRole("존재하지 않는 권한"))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("delegateRole - 리더 권한 위임 성공 테스트")
    void delegateRole_Success() {
        // when
        loginMember.delegateRole(targetMember, TeamMemberRole.LEADER);

        // then
        assertThat(loginMember.getRole().getDisplayName()).isEqualTo("일반멤버");
        assertThat(targetMember.getRole().getDisplayName()).isEqualTo("회장");
    }

    @Test
    @DisplayName("delegateRole - 권한 위임 권한이 없는 경우 예외 발생 테스트")
    void delegateRole_NoAuthority_Exception() {
        // when & then
        assertThatThrownBy(() -> basicMember.delegateRole(targetMember, TeamMemberRole.LEADER))
            .isInstanceOf(NoPermissionException.class);
    }

    @Test
    @DisplayName("validateJoinDecisionAuthority - 가입 승인 권한이 있는 경우 성공 테스트")
    void validateJoinDecisionAuthority_Success() {
        // when & then
        loginMember.validateJoinDecisionAuthority();
    }

    @Test
    @DisplayName("validateJoinDecisionAuthority - 가입 승인 권한이 없는 경우 예외 발생 테스트")
    void validateJoinDecisionAuthority_NoAuthority_Exception() {
        // when & then
        assertThatThrownBy(() -> basicMember.validateJoinDecisionAuthority())
            .isInstanceOf(NoPermissionException.class);
    }

    @Test
    @DisplayName("validateKickAuthority - 추방 권한이 있는 경우 성공 테스트")
    void validateKickAuthority_Success() {
        // when & then
        loginMember.validateKickAuthority(targetMember);
    }

    @Test
    @DisplayName("validateKickAuthority - 추방 권한이 없는 경우 예외 발생 테스트")
    void validateKickAuthority_NoAuthority_Exception() {
        // when & then
        assertThatThrownBy(() -> basicMember.validateKickAuthority(loginMember))
            .isInstanceOf(NoPermissionException.class);
    }
}
