package com.shootdoori.match.team.domain;

import com.shootdoori.match.entity.common.TimeStamp;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(
    name = "team_member",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"team_id", "user_id"})
    }
)
@EntityListeners(AuditingEntityListener.class)
public class TeamMember {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "team_member_role", nullable = false, length = 20)
    private TeamMemberRole role = TeamMemberRole.MEMBER;

    @Embedded
    private TimeStamp timeStamp = new TimeStamp();

    protected TeamMember() {
    }

    protected TeamMember(Team team, Long userId, String role) {
        this.team = team;
        this.userId = userId;
        this.role = TeamMemberRole.fromDisplayName(role);
    }

    public static TeamMember of(Team team, Long userId, String role) {
        return new TeamMember(team, userId, role);
    }

    public Long getId() {
        return id;
    }

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

    public void changeRole(String role) {
        this.role = TeamMemberRole.fromDisplayName(role);
    }

    public void delegateRole(TeamMember targetMember, TeamMemberRole role) {
        validateJoinDecisionAuthority();
        this.role = TeamMemberRole.MEMBER;
        targetMember.role = role;
    }

    public void validateJoinDecisionAuthority() {
        role.validateJoinDecisionAuthority();
    }
}