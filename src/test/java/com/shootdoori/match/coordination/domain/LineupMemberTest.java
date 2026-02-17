package com.shootdoori.match.coordination.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.shootdoori.match.entity.common.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LineupMemberTest {

    private static final Long TEAM_MEMBER_ID = 1L;

    @Test
    @DisplayName("유효한 값으로 LineupMember 생성")
    void 유효한_값으로_LineupMember_생성() {
        // given
        LineupMembers lineupMembers = new LineupMembers();

        // when
        LineupMember lineupMember = new LineupMember(lineupMembers, TEAM_MEMBER_ID, Position.GK, true);

        // then
        assertThat(lineupMember.getTeamMemberId()).isEqualTo(TEAM_MEMBER_ID);
        assertThat(lineupMember.getPosition()).isEqualTo(Position.GK);
        assertThat(lineupMember.isStarter()).isTrue();
    }

    @Test
    @DisplayName("lineupMembers가 null이면 LineupMember 생성 실패")
    void lineupMembers가_null인_경우_생성_실패() {
        // when & then
        assertThatThrownBy(() -> new LineupMember(null, TEAM_MEMBER_ID, Position.GK, true))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("teamMemberId가 null이면 LineupMember 생성 실패")
    void teamMemberId가_null인_경우_생성_실패() {
        // given
        LineupMembers lineupMembers = new LineupMembers();

        // when & then
        assertThatThrownBy(() -> new LineupMember(lineupMembers, null, Position.GK, true))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("position이 null이면 LineupMember 생성 실패")
    void position이_null인_경우_생성_실패() {
        // given
        LineupMembers lineupMembers = new LineupMembers();

        // when & then
        assertThatThrownBy(() -> new LineupMember(lineupMembers, TEAM_MEMBER_ID, null, true))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("isStarter가 true면 선발 멤버이다")
    void isStarter가_true면_선발_멤버이다() {
        // given
        LineupMembers lineupMembers = new LineupMembers();
        LineupMember lineupMember = new LineupMember(lineupMembers, TEAM_MEMBER_ID, Position.GK, true);

        // when & then
        assertThat(lineupMember.isStarter()).isTrue();
    }

    @Test
    @DisplayName("isStarter가 false면 비선발 멤버이다")
    void isStarter가_false면_비선발_멤버이다() {
        // given
        LineupMembers lineupMembers = new LineupMembers();
        LineupMember lineupMember = new LineupMember(lineupMembers, TEAM_MEMBER_ID, Position.GK, false);

        // when & then
        assertThat(lineupMember.isStarter()).isFalse();
    }

    @Test
    @DisplayName("같은 teamMemberId면 true를 반환한다")
    void 같은_teamMemberId면_true를_반환한다() {
        // given
        LineupMembers lineupMembers = new LineupMembers();
        LineupMember lineupMember = new LineupMember(lineupMembers, TEAM_MEMBER_ID, Position.GK, true);

        // when
        boolean result = lineupMember.isSameTeamMember(TEAM_MEMBER_ID);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("다른 teamMemberId면 false를 반환한다")
    void 다른_teamMemberId면_false를_반환한다() {
        // given
        LineupMembers lineupMembers = new LineupMembers();
        LineupMember lineupMember = new LineupMember(lineupMembers, TEAM_MEMBER_ID, Position.GK, true);

        // when
        boolean result = lineupMember.isSameTeamMember(2L);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("비교 대상 teamMemberId가 null이면 false를 반환한다")
    void 비교_대상_teamMemberId가_null이면_false를_반환한다() {
        // given
        LineupMembers lineupMembers = new LineupMembers();
        LineupMember lineupMember = new LineupMember(lineupMembers, TEAM_MEMBER_ID, Position.GK, true);

        // when
        boolean result = lineupMember.isSameTeamMember(null);

        // then
        assertThat(result).isFalse();
    }
}
