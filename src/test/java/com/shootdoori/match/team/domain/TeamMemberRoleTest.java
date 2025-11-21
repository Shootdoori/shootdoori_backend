package com.shootdoori.match.team.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.shootdoori.match.exception.common.NoPermissionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("TeamMemberRole 테스트")
class TeamMemberRoleTest {
    private final static TeamMemberRole LEADER_ROLE = TeamMemberRole.LEADER;
    private final static TeamMemberRole VICE_LEADER_ROLE = TeamMemberRole.VICE_LEADER;
    private final static TeamMemberRole MEMBER_ROLE = TeamMemberRole.MEMBER;

    @Test
    @DisplayName("fromDisplayName - 유효한 역할 이름으로 Enum 조회 성공")
    void fromDisplayName_Success() {
        // when
        TeamMemberRole role = TeamMemberRole.fromDisplayName("회장");

        // then
        assertThat(role).isEqualTo(LEADER_ROLE);
    }

    @Test
    @DisplayName("fromDisplayName - 존재하지 않는 역할 이름으로 조회 시 예외 발생")
    void fromDisplayName_InvalidName_Exception() {
        // when & then
        assertThatThrownBy(() -> TeamMemberRole.fromDisplayName("존재하지 않는 역할"))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("canKick - 회장(Leader)은 부회장을 추방할 수 있다")
    void canKick_Leader_Can_Kick_ViceLeader() {
        // when
        boolean canKick = LEADER_ROLE.canKick(VICE_LEADER_ROLE);

        // then
        assertThat(canKick).isEqualTo(true);
    }

    @Test
    @DisplayName("canKick - 회장(Leader)은 일반 멤버를 추방할 수 있다")
    void canKick_Leader_Can_Kick_Member() {
        // when
        boolean canKick = LEADER_ROLE.canKick(MEMBER_ROLE);

        // then
        assertThat(canKick).isEqualTo(true);
    }

    @Test
    @DisplayName("canKick - 부회장(ViceLeader)은 일반 멤버를 추방할 수 있다")
    void canKick_ViceLeader_Can_Kick_Member() {
        // when
        boolean canKick = VICE_LEADER_ROLE.canKick(MEMBER_ROLE);

        // then
        assertThat(canKick).isEqualTo(true);
    }

    @Test
    @DisplayName("canKick - 부회장(ViceLeader)은 회장을 추방할 수 없다")
    void canKick_ViceLeader_Cannot_Kick_Leader() {
        // when
        boolean canKick = VICE_LEADER_ROLE.canKick(LEADER_ROLE);

        // then
        assertThat(canKick).isEqualTo(false);
    }

    @Test
    @DisplayName("canKick - 일반 멤버(Member)는 회장을 추방할 수 없다")
    void canKick_Member_Cannot_Kick_Leader() {
        // when
        boolean canKick = MEMBER_ROLE.canKick(LEADER_ROLE);

        // then
        assertThat(canKick).isEqualTo(false);
    }

    @Test
    @DisplayName("canKick - 일반 멤버(Member)는 부회장을 추방할 수 없다")
    void canKick_Member_Cannot_Kick_ViceLeader() {
        // when
        boolean canKick = MEMBER_ROLE.canKick(VICE_LEADER_ROLE);

        // then
        assertThat(canKick).isEqualTo(false);
    }

    @Test
    @DisplayName("validateJoinDecisionAuthority - 회장은 가입 승인 권한이 있다")
    void validateJoinDecisionAuthority_Leader_Success() {
        // when & then
        LEADER_ROLE.validateJoinDecisionAuthority();
    }

    @Test
    @DisplayName("validateJoinDecisionAuthority - 부회장은 가입 승인 권한이 있다")
    void validateJoinDecisionAuthority_ViceLeader_Success() {
        // when & then
        VICE_LEADER_ROLE.validateJoinDecisionAuthority();
    }

    @Test
    @DisplayName("validateJoinDecisionAuthority - 일반 멤버는 가입 승인 권한이 없다")
    void validateJoinDecisionAuthority_Fail() {
        // when & then
        assertThatThrownBy(() -> MEMBER_ROLE.validateJoinDecisionAuthority())
            .isInstanceOf(NoPermissionException.class);
    }
}
