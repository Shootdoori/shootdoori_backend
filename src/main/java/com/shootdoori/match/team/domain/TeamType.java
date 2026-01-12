package com.shootdoori.match.team.domain;

import java.util.stream.Stream;

public enum TeamType {
    CENTRAL_CLUB("중앙동아리"),
    DEPARTMENT_CLUB("과동아리"),
    OTHER("기타");

    private final String displayName;

    TeamType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static TeamType fromDisplayName(String displayName) {
        return Stream.of(values())
            .filter(type -> type.displayName.equals(displayName))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unknown team type: " + displayName));
    }
}

