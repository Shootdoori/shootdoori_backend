package com.shootdoori.match.coordination.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PreferredSchedule {

    @Column(name = "preferred_date", nullable = false)
    private LocalDate preferredDate;

    @Column(name = "preferred_time_start", nullable = false)
    private LocalTime preferredTimeStart;

    @Column(name = "preferred_time_end", nullable = false)
    private LocalTime preferredTimeEnd;

    protected PreferredSchedule() {
    }

    public PreferredSchedule(LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.preferredDate = date;
        this.preferredTimeStart = startTime;
        this.preferredTimeEnd = endTime;

        validateSchedule();
    }

    public LocalDate getPreferredDate() {
        return preferredDate;
    }

    public LocalTime getPreferredTimeStart() {
        return preferredTimeStart;
    }

    public LocalTime getPreferredTimeEnd() {
        return preferredTimeEnd;
    }

    private void validateSchedule() {
        if (preferredDate == null || preferredTimeStart == null || preferredTimeEnd == null) {
            throw new IllegalArgumentException("선호 일정 값은 비어 있을 수 없습니다.");
        }

        if (!preferredTimeStart.isBefore(preferredTimeEnd)) {
            throw new IllegalArgumentException("선호 시작 시간은 종료 시간보다 빨라야 합니다.");
        }
    }
}
