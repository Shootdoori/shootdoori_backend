package com.shootdoori.match.coordination.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MatchTest {

    private static final Long HOME_TEAM_ID = 1L;
    private static final Long AWAY_TEAM_ID = 2L;
    private static final Long VENUE_ID = 1L;
    private static final Long HOME_LINEUP_ID = 10L;
    private static final Long AWAY_LINEUP_ID = 20L;
    private static final LocalDate PREFERRED_DATE = LocalDate.of(2026, 2, 10);
    private static final LocalTime PREFERRED_TIME_START = LocalTime.of(14, 0);
    private static final LocalTime PREFERRED_TIME_END = LocalTime.of(16, 0);

    @Test
    @DisplayName("매치 생성 시 만료시간은 선호 날짜 하루 전 23:59:59로 계산된다")
    void 매치_생성_시_만료시간을_올바르게_계산한다() {
        // when
        Match match = createMatch();

        // then
        LocalDateTime expectedExpiresAt = LocalDateTime.of(2026, 2, 9, 23, 59, 59);
        assertThat(match.getExpiresAt()).isEqualTo(expectedExpiresAt);
    }

    @Test
    @DisplayName("매치 생성 시 초기 상태는 WAITING이다")
    void 매치_생성_시_초기_상태가_WAITING이다() {
        // when
        Match match = createMatch();

        // then
        assertThat(match.getStatus()).isEqualTo(MatchStatus.WAITING);
        assertThat(match.getHomeTeamId()).isEqualTo(HOME_TEAM_ID);
        assertThat(match.getAwayTeamId()).isNull();
        assertThat(match.getMatchAt()).isNull();
    }

    @Test
    @DisplayName("매치 생성 시 모든 필드가 올바르게 설정된다")
    void 매치_생성_시_모든_필드가_올바르게_설정된다() {
        // when
        Match match = createMatch();

        // then
        assertThat(match.getHomeTeamId()).isEqualTo(HOME_TEAM_ID);
        assertThat(match.getHomeLineupId()).isEqualTo(HOME_LINEUP_ID);
        assertThat(match.getPreferredDate()).isEqualTo(PREFERRED_DATE);
        assertThat(match.getPreferredTimeStart()).isEqualTo(PREFERRED_TIME_START);
        assertThat(match.getPreferredTimeEnd()).isEqualTo(PREFERRED_TIME_END);
        assertThat(match.getVenueId()).isEqualTo(VENUE_ID);
        assertThat(match.getStatus()).isEqualTo(MatchStatus.WAITING);
        assertThat(match.getAwayTeamId()).isNull();
        assertThat(match.getAwayLineupId()).isNull();
        assertThat(match.getMatchAt()).isNull();
    }

    @Test
    @DisplayName("매치 성사 시 원정팀과 매치 시간이 설정되고 상태가 MATCHED로 변경된다")
    void 매치_성사_시_상태와_정보가_올바르게_변경된다() {
        // given
        Match match = createMatch();
        LocalDateTime matchAt = LocalDateTime.of(2026, 2, 10, 15, 0);

        // when
        match.match(AWAY_TEAM_ID, AWAY_LINEUP_ID, matchAt);

        // then
        assertThat(match.getAwayTeamId()).isEqualTo(AWAY_TEAM_ID);
        assertThat(match.getAwayLineupId()).isEqualTo(AWAY_LINEUP_ID);
        assertThat(match.getMatchAt()).isEqualTo(matchAt);
        assertThat(match.getStatus()).isEqualTo(MatchStatus.MATCHED);
    }

    @Test
    @DisplayName("매치 취소 시 상태가 CANCELED로 변경된다")
    void 매치_취소_시_상태가_올바르게_변경된다() {
        // given
        Match match = createMatch();

        // when
        match.cancel();

        // then
        assertThat(match.getStatus()).isEqualTo(MatchStatus.CANCELED);
    }

    @Test
    @DisplayName("취소는 대기중 또는 매치됨 상태에서만 가능하다")
    void 취소는_대기중_또는_매치됨_상태에서만_가능하다() {
        // given
        Match canceledMatch = createMatch();
        canceledMatch.cancel();

        Match finishedMatch = createMatchedMatch();
        finishedMatch.finish();

        // when & then
        assertThatThrownBy(() -> canceledMatch.cancel()).isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(() -> finishedMatch.cancel()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("매치 종료 시 상태가 FINISHED로 변경된다")
    void 매치_종료_시_상태가_올바르게_변경된다() {
        // given
        Match match = createMatchedMatch();

        // when
        match.finish();

        // then
        assertThat(match.getStatus()).isEqualTo(MatchStatus.FINISHED);
    }

    @Test
    @DisplayName("종료는 매치됨 상태에서만 가능하다")
    void 종료는_매치됨_상태에서만_가능하다() {
        // given
        Match waitingMatch = createMatch();

        Match canceledMatch = createMatch();
        canceledMatch.cancel();

        Match finishedMatch = createMatchedMatch();
        finishedMatch.finish();

        // when & then
        assertThatThrownBy(() -> waitingMatch.finish()).isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(() -> canceledMatch.finish()).isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(() -> finishedMatch.finish()).isInstanceOf(IllegalStateException.class);
    }

    private Match createMatch() {
        return new Match(
            HOME_TEAM_ID,
            PREFERRED_DATE,
            PREFERRED_TIME_START,
            PREFERRED_TIME_END,
            VENUE_ID,
            HOME_LINEUP_ID
        );
    }

    private Match createMatchedMatch() {
        Match match = createMatch();
        match.match(AWAY_TEAM_ID, AWAY_LINEUP_ID, LocalDateTime.of(2026, 2, 10, 15, 0));
        return match;
    }
}