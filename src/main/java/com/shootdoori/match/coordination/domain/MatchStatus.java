package com.shootdoori.match.coordination.domain;

public enum MatchStatus {
    WAITING("대기중"),
    MATCHED("매치됨"),
    CANCELED("취소됨"),
    FINISHED("종료됨");

    private final String description;

    MatchStatus(String description) { this.description = description; }

    public String getDescription() {
        return description;
    }

    public void validateMatchable() {
        if (!canMatch()) {
            throw new IllegalStateException("매칭할 수 없는 상태입니다.");
        }
    }

    public void validateCancelable() {
        if (!canCancel()) {
            throw new IllegalStateException("취소할 수 없는 상태입니다.");
        }
    }

    public void validateFinishable() {
        if (!canFinish()) {
            throw new IllegalStateException("종료할 수 없는 상태입니다.");
        }
    }

    private boolean canMatch() {
        return this == WAITING;
    }

    private boolean canFinish() {
        return this == MATCHED;
    }

    private boolean canCancel() {
        return this == WAITING || this == MATCHED;
    }
}
