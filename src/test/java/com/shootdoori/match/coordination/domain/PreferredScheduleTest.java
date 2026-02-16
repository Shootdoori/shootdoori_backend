package com.shootdoori.match.coordination.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PreferredScheduleTest {

    @Test
    @DisplayName("유효한 값으로 PreferredSchedule 생성")
    void 유효한_값으로_PreferredSchedule_생성() {
        // given
        LocalDate date = LocalDate.of(2026, 2, 10);
        LocalTime startTime = LocalTime.of(14, 0);
        LocalTime endTime = LocalTime.of(16, 0);

        // when
        PreferredSchedule preferredSchedule = new PreferredSchedule(date, startTime, endTime);

        // then
        assertThat(preferredSchedule.getPreferredDate()).isEqualTo(date);
        assertThat(preferredSchedule.getPreferredTimeStart()).isEqualTo(startTime);
        assertThat(preferredSchedule.getPreferredTimeEnd()).isEqualTo(endTime);
    }

    @Test
    @DisplayName("date가 null이면 PreferredSchedule 생성 실패")
    void date가_null인_경우_생성_실패() {
        assertThatThrownBy(() -> new PreferredSchedule(null, LocalTime.of(10, 0), LocalTime.of(12, 0)))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("startTime이 null이면 PreferredSchedule 생성 실패")
    void startTime이_null인_경우_생성_실패() {
        // when & then
        assertThatThrownBy(() -> new PreferredSchedule(LocalDate.of(2026, 2, 10), null, LocalTime.of(12, 0)))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("endTime이 null이면 PreferredSchedule 생성 실패")
    void endTime이_null인_경우_생성_실패() {
        // when & then
        assertThatThrownBy(() -> new PreferredSchedule(LocalDate.of(2026, 2, 10), LocalTime.of(10, 0), null))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("PreferredSchedule의 시작시간은 종료시간보다 이전이어야 합니다.")
    void PreferredSchedule의_시작시간은_종료시간보다_이전이어야_합니다() {
        // when & then
        assertThatThrownBy(() ->
            new PreferredSchedule(LocalDate.of(2026, 2, 10), LocalTime.of(16, 0), LocalTime.of(14, 0))
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
