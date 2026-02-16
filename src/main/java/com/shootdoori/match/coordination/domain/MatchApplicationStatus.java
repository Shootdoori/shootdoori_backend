package com.shootdoori.match.coordination.domain;

public enum MatchApplicationStatus {
    PENDING("신청대기중"),
    ACCEPTED("수락됨"),
    REJECTED("거절됨"),
    CANCELED("취소됨");

    private final String description;

    MatchApplicationStatus(String description) { this.description = description; }

    public void validatePending() {
        if (!isPending()) {
            throw new IllegalStateException("신청 대기중 상태가 아닙니다.");
        }
    }

    public String getDescription() {
        return description;
    }

    private boolean isPending() {
        return this == PENDING;
    }
}
