package com.shootdoori.match.joinWaiting.domain;

import java.util.stream.Stream;

public enum JoinWaitingType {
    MEMBER("팀원"),
    MERCENARY("용병");

    private final String displayName;

    JoinWaitingType(String displayName) { this.displayName = displayName; }

    public String getDisplayName() { return displayName; }

    public static JoinWaitingType fromDisplayName(String displayName) {
        return Stream.of(values())
            .filter(type -> type.displayName.equals(displayName))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unknown join queue status: " + displayName));
    }
}
