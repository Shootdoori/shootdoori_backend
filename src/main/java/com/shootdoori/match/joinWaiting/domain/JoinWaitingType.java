package com.shootdoori.match.joinWaiting.domain;

public enum JoinWaitingType {
    MEMBER("팀원"),
    MERCENARY("용병");

    private final String displayName;

    JoinWaitingType(String displayName) { this.displayName = displayName; }

    public String getDisplayName() { return displayName; }

    public static JoinWaitingType fromDisplayName(String displayName) {
        for (JoinWaitingType type : values()) {
            if (type.displayName.equals(displayName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown join queue status: " + displayName);
    }
}
