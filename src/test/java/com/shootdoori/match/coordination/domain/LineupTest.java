package com.shootdoori.match.coordination.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.shootdoori.match.entity.common.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LineupTest {

    private static final Long MATCH_ID = 1L;
    private static final Long TEAM_ID = 10L;

    @Test
    @DisplayName("Lineup 생성 시 matchId와 members가 초기화된다")
    void 생성_시_matchId와_members가_초기화된다() {
        // when
        Lineup lineup = new Lineup(MATCH_ID, TEAM_ID);

        // then
        assertThat(lineup.getTeamId()).isEqualTo(TEAM_ID);
        assertThat(lineup.getMatchId()).isEqualTo(MATCH_ID);
        assertThat(lineup.getMembers()).isNotNull();
        assertThat(lineup.getTotalMemberCount()).isZero();
    }

    @Test
    @DisplayName("멤버 추가를 위임하면 총 멤버 수가 증가한다")
    void 멤버_추가를_위임하면_총_멤버_수가_증가한다() {
        // given
        Lineup lineup = new Lineup(MATCH_ID, TEAM_ID);

        // when
        lineup.addMember(1L, Position.GK, true);

        // then
        assertThat(lineup.getTotalMemberCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("멤버 제거를 위임하면 총 멤버 수가 감소한다")
    void 멤버_제거를_위임하면_총_멤버_수가_감소한다() {
        // given
        Lineup lineup = new Lineup(MATCH_ID, TEAM_ID);
        lineup.addMember(1L, Position.GK, true);
        lineup.addMember(2L, Position.DF, false);

        // when
        lineup.removeMember(1L);

        // then
        assertThat(lineup.getTotalMemberCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("라인업 인원이 11명 미만이면 유효하지 않다")
    void 라인업_인원이_11명_미만이면_유효하지_않다() {
        // given
        Lineup lineup = new Lineup(MATCH_ID, TEAM_ID);
        for (long i = 1; i <= 10; i++) {
            lineup.addMember(i, Position.AM, true);
        }

        // when & then
        assertThat(lineup.isValidLineup()).isFalse();
    }

    @Test
    @DisplayName("라인업 인원이 11명이면 유효하다")
    void 라인업_인원이_11명이면_유효하다() {
        // given
        Lineup lineup = new Lineup(MATCH_ID, TEAM_ID);
        for (long i = 1; i <= 11; i++) {
            lineup.addMember(i, Position.AM, true);
        }

        // when & then
        assertThat(lineup.isValidLineup()).isTrue();
    }

    @Test
    @DisplayName("총 멤버 수를 정상 반환한다")
    void 총_멤버_수를_정상_반환한다() {
        // given
        Lineup lineup = new Lineup(MATCH_ID, TEAM_ID);

        // when
        lineup.addMember(1L, Position.GK, true);
        lineup.addMember(2L, Position.DF, false);

        // then
        assertThat(lineup.getTotalMemberCount()).isEqualTo(2);
    }
}
