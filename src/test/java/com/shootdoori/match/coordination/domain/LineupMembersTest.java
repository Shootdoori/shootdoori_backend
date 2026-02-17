package com.shootdoori.match.coordination.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.shootdoori.match.entity.common.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LineupMembersTest {

    @Test
    @DisplayName("멤버 추가 시 인원이 정상적으로 추가된다")
    void 멤버_추가_시_인원이_정상적으로_추가된다() {
        // given
        LineupMembers lineupMembers = new LineupMembers();

        // when
        lineupMembers.addMember(1L, Position.AM, true);

        // then
        assertThat(lineupMembers.getTotalCount()).isEqualTo(1);
        assertThat(lineupMembers.getMembers()).anyMatch(
            member -> member.getTeamMemberId().equals(1L));
    }

    @Test
    @DisplayName("중복된 멤버 추가 시 예외를 발생한다")
    void 중복된_멤버_추가_시_예외를_발생한다() {
        // given
        LineupMembers lineupMembers = new LineupMembers();

        // when & then
        lineupMembers.addMember(1L, Position.AM, true);
        assertThatThrownBy(() -> lineupMembers.addMember(1L, Position.AM, true))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("인원 초과 시 예외를 발생한다")
    void 인원_초과_시_예외를_발생한다() {
        // given
        LineupMembers lineupMembers = new LineupMembers();
        addMembers(lineupMembers, 1, 11, true, Position.AM);
        addMembers(lineupMembers, 12, 18, false, Position.DF);

        // when & then
        assertThatThrownBy(() -> lineupMembers.addMember(19L, Position.DF, false))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("선발 인원 초과 시 예외를 발생한다")
    void 선발_인원_초과_시_예외를_발생한다() {
        // given
        LineupMembers lineupMembers = new LineupMembers();
        addMembers(lineupMembers, 1, 11, true, Position.AM);

        // when & then
        assertThatThrownBy(() -> lineupMembers.addMember(12L, Position.DF, true))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("멤버 제거 시 인원이 정상적으로 감소한다")
    void 멤버_제거_시_인원이_정상적으로_감소한다() {
        // given
        LineupMembers lineupMembers = new LineupMembers();
        lineupMembers.addMember(1L, Position.AM, true);
        lineupMembers.addMember(2L, Position.DF, false);

        // when
        lineupMembers.removeMember(1L);

        // then
        assertThat(lineupMembers.getTotalCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("라인업에 존재하지 않는 멤버를 제거해도 예외 없이 유지된다")
    void 존재하지_않는_멤버를_제거해도_변화가_없다() {
        // given
        LineupMembers lineupMembers = new LineupMembers();
        lineupMembers.addMember(1L, Position.AM, true);
        lineupMembers.addMember(2L, Position.DF, false);

        // when
        lineupMembers.removeMember(3L);

        // then
        assertThat(lineupMembers.getTotalCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("최종 라인업 인원이 11명 미만이면 유효하지 않다")
    void 최종_라인업_인원이_11명_미만이면_유효하지_않다() {
        // given
        LineupMembers lineupMembers = new LineupMembers();
        addMembers(lineupMembers, 1, 10, true, Position.AM);

        // when & then
        assertThat(lineupMembers.isValidFinalLineup()).isFalse();
    }

    @Test
    @DisplayName("최종 라인업 인원이 11명이면 유효하다")
    void 최종_라인업_인원이_11명이면_유효하다() {
        // given
        LineupMembers lineupMembers = new LineupMembers();
        addMembers(lineupMembers, 1, 11, true, Position.AM);

        // when & then
        assertThat(lineupMembers.isValidFinalLineup()).isTrue();
    }

    @Test
    @DisplayName("최종 라인업 인원이 18명이면 유효하다")
    void 최종_라인업_인원이_18명이면_유효하다() {
        // given
        LineupMembers lineupMembers = new LineupMembers();
        addMembers(lineupMembers, 1, 11, true, Position.AM);
        addMembers(lineupMembers, 12, 18, false, Position.DF);

        // when & then
        assertThat(lineupMembers.isValidFinalLineup()).isTrue();
    }

    @Test
    @DisplayName("라인업이 비어 있으면 true를 반환한다")
    void 라인업이_비어_있으면_true를_반환한다() {
        // given
        LineupMembers lineupMembers = new LineupMembers();

        // when & then
        assertThat(lineupMembers.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("라인업이 비어 있지 않으면 false를 반환한다")
    void 라인업이_비어_있으면_false를_반환한다() {
        // given
        LineupMembers lineupMembers = new LineupMembers();
        lineupMembers.addMember(1L, Position.AM, true);

        // when & then
        assertThat(lineupMembers.isEmpty()).isFalse();
    }

    private void addMembers(LineupMembers lineupMembers, long from, long to, boolean isStarter,
        Position position) {
        for (long i = from; i <= to; i++) {
            lineupMembers.addMember(i, position, isStarter);
        }
    }
}
