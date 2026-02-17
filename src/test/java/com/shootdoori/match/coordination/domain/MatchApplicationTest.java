package com.shootdoori.match.coordination.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.shootdoori.match.exception.common.NoPermissionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MatchApplicationTest {

    private static final Long MATCH_ID = 1L;
    private static final Long REQUEST_TEAM_ID = 10L;
    private static final Long OTHER_TEAM_ID = 20L;
    private static final Long LINEUP_ID = 100L;
    private static final String REQUEST_MESSAGE = "매치 신청합니다.";

    @Test
    @DisplayName("생성 시 기본 상태는 PENDING이다")
    void 생성_시_기본_상태는_PENDING이다() {
        // given
        MatchApplication application = createApplication();

        // when & then
        assertThat(application.getStatus()).isEqualTo(MatchApplicationStatus.PENDING);
    }

    @Test
    @DisplayName("생성 시 입력 필드가 올바르게 저장된다")
    void 생성_시_입력_필드가_올바르게_저장된다() {
        // given
        MatchApplication application = createApplication();

        // when & then
        assertThat(application.getMatchId()).isEqualTo(MATCH_ID);
        assertThat(application.getRequestTeamId()).isEqualTo(REQUEST_TEAM_ID);
        assertThat(application.getLineupId()).isEqualTo(LINEUP_ID);
        assertThat(application.getRequestMessage()).isEqualTo(REQUEST_MESSAGE);
    }

    @Test
    @DisplayName("수락 시 상태가 ACCEPTED로 변경되고 처리 팀 ID가 저장된다")
    void 수락_시_상태와_처리팀이_변경된다() {
        // given
        MatchApplication application = createApplication();

        // when
        application.accept(OTHER_TEAM_ID);

        // then
        assertThat(application.getStatus()).isEqualTo(MatchApplicationStatus.ACCEPTED);
        assertThat(application.getProcessedByTeamId()).isEqualTo(OTHER_TEAM_ID);
    }

    @Test
    @DisplayName("거절 시 상태가 REJECTED로 변경되고 처리 팀 ID가 저장된다")
    void 거절_시_상태와_처리팀이_변경된다() {
        // given
        MatchApplication application = createApplication();

        // when
        application.reject(OTHER_TEAM_ID);

        // then
        assertThat(application.getStatus()).isEqualTo(MatchApplicationStatus.REJECTED);
        assertThat(application.getProcessedByTeamId()).isEqualTo(OTHER_TEAM_ID);
    }

    @Test
    @DisplayName("요청팀이 취소하면 상태가 CANCELED로 변경되고 처리 팀 ID가 저장된다")
    void 취소_시_상태와_처리팀이_변경된다() {
        // given
        MatchApplication application = createApplication();

        // when
        application.cancel(REQUEST_TEAM_ID);

        // then
        assertThat(application.getStatus()).isEqualTo(MatchApplicationStatus.CANCELED);
        assertThat(application.getProcessedByTeamId()).isEqualTo(REQUEST_TEAM_ID);
    }

    @Test
    @DisplayName("요청팀과 로그인 팀이 다르면 취소할 수 없다")
    void 요청팀이_아닌_경우_취소_실패() {
        // given
        MatchApplication application = createApplication();

        // when & then
        assertThatThrownBy(() -> application.cancel(OTHER_TEAM_ID)).isInstanceOf(
            NoPermissionException.class);
    }

    @Test
    @DisplayName("PENDING이 아닌 상태에서는 수락할 수 없다")
    void 대기중이_아닌_상태에서_수락_실패() {
        // given
        MatchApplication application = createAcceptedApplication();

        // when & then
        assertThatThrownBy(() -> application.accept(OTHER_TEAM_ID)).isInstanceOf(
            IllegalStateException.class);
    }

    @Test
    @DisplayName("PENDING이 아닌 상태에서는 거절할 수 없다")
    void 대기중이_아닌_상태에서_거절_실패() {
        // given
        MatchApplication application = createRejectedApplication();

        // when & then
        assertThatThrownBy(() -> application.reject(OTHER_TEAM_ID)).isInstanceOf(
            IllegalStateException.class);
    }

    @Test
    @DisplayName("PENDING이 아닌 상태에서는 취소할 수 없다")
    void 대기중이_아닌_상태에서_취소_실패() {
        // given
        MatchApplication application = createCanceledApplication();

        // when & then
        assertThatThrownBy(() -> application.cancel(REQUEST_TEAM_ID)).isInstanceOf(
            IllegalStateException.class);
    }

    private MatchApplication createApplication() {
        return new MatchApplication(MATCH_ID, REQUEST_TEAM_ID, LINEUP_ID, REQUEST_MESSAGE);
    }

    private MatchApplication createAcceptedApplication() {
        MatchApplication application = createApplication();
        application.accept(OTHER_TEAM_ID);
        return application;
    }

    private MatchApplication createRejectedApplication() {
        MatchApplication application = createApplication();
        application.reject(OTHER_TEAM_ID);
        return application;
    }

    private MatchApplication createCanceledApplication() {
        MatchApplication application = createApplication();
        application.cancel(REQUEST_TEAM_ID);
        return application;
    }
}
