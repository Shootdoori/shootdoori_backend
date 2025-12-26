package com.shootdoori.match.joinWaiting.domain;

import java.util.stream.Stream;

public enum JoinWaitingStatus {
    PENDING("대기중"),
    APPROVED("승인됨"),
    REJECTED("거절됨"),
    CANCELED("취소됨");

    private final String displayName;

    JoinWaitingStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static JoinWaitingStatus fromDisplayName(String displayName) {
        return Stream.of(values())
            .filter(status -> status.displayName.equals(displayName))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(
                "Unknown join queue status: " + displayName
            ));
    }

    public boolean isPending() {
        return this == PENDING;
    }
}
