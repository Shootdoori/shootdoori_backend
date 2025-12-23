package com.shootdoori.match.team.domain.value;

import com.shootdoori.match.exception.common.ErrorCode;
import com.shootdoori.match.exception.domain.team.TeamNameException;
import java.util.Objects;

public class TeamName {

    private static final int MAX_TEAM_NAME_LENGTH = 100;

    private String teamName;

    private TeamName(String teamName) {
        if (teamName == null || teamName.isBlank()) {
            throw new TeamNameException(ErrorCode.INVALID_TEAM_NAME);
        }

        validate(teamName);
        this.teamName = teamName;
    }

    public static TeamName of(String name) {
        return new TeamName(name);
    }

    private void validate(String teamName) {
        if (teamName.length() > MAX_TEAM_NAME_LENGTH) {
            throw new TeamNameException(ErrorCode.TEAM_NAME_TOO_LONG);
        }
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TeamName teamName1 = (TeamName) o;
        return Objects.equals(teamName, teamName1.teamName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(teamName);
    }
}
