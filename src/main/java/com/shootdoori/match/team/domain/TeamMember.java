package com.shootdoori.match.team.domain;

import com.shootdoori.match.entity.common.TimeStamp;
import java.time.LocalDateTime;

public class TeamMember {

    private Long id;

    private Team team;

    private Long userId;

    private TeamMemberRole role = TeamMemberRole.MEMBER;

    private TimeStamp timeStamp;

    protected TeamMember() { }

    protected TeamMember(Team team, Long userId, String role) {
        this.team = team;
        this.userId = userId;
        this.role = TeamMemberRole.fromDisplayName(role);
    }

    public static TeamMember of(Team team, Long userId, String role) {
        return new TeamMember(team, userId, role);
    }

    public Long getId() { return id; }

    public Team getTeam() {
        return team;
    }

    public Long getUserId() {
        return userId;
    }

    public TeamMemberRole getRole() {
        return role;
    }

    public LocalDateTime getCreatedAt() {
        return timeStamp.getCreatedAt();
    }

    public LocalDateTime getUpdatedAt() {
        return timeStamp.getUpdatedAt();
    }

    public void validateJoinDecisionAuthority() { role.validateJoinDecisionAuthority(); }
}