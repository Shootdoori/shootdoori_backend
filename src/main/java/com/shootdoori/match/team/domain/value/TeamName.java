package com.shootdoori.match.team.domain.value;

import com.shootdoori.match.exception.common.ErrorCode;
import com.shootdoori.match.exception.domain.team.TeamNameException;

public record TeamName(String teamName) {
    private static final int MAX_TEAM_NAME_LENGTH = 100;

    public TeamName {
        if (teamName == null || teamName.isBlank()) {
            throw new TeamNameException(ErrorCode.INVALID_TEAM_NAME);
        }

        if (teamName.length() > MAX_TEAM_NAME_LENGTH) {
            throw new TeamNameException(ErrorCode.TEAM_NAME_TOO_LONG);
        }
    }

    public static TeamName of(String name) {
        return new TeamName(name);
    }
}
