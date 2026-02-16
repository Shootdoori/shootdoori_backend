package com.shootdoori.match.coordination.domain;

import com.shootdoori.match.exception.common.BusinessException;
import com.shootdoori.match.exception.common.ErrorCode;

public enum MatchApplicationStatus {
    PENDING("신청대기중"),
    ACCEPTED("수락됨"),
    REJECTED("거절됨"),
    CANCELED("취소됨");

    private final String description;

    MatchApplicationStatus(String description) { this.description = description; }

    public void validatePending() {
        if (!isPending()) {
            throw new BusinessException(ErrorCode.ALREADY_MATCH_REQUEST);
        }
    }

    public String getDescription() {
        return description;
    }

    private boolean isPending() {
        return this == PENDING;
    }
}
