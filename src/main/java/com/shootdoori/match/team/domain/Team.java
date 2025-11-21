package com.shootdoori.match.team.domain;

import com.shootdoori.match.entity.common.TimeStamp;
import com.shootdoori.match.team.domain.TeamType;
import com.shootdoori.match.team.domain.value.Description;
import com.shootdoori.match.team.domain.value.TeamName;
import com.shootdoori.match.team.domain.value.UniversityName;
import com.shootdoori.match.exception.common.ErrorCode;
import com.shootdoori.match.exception.common.NoPermissionException;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "team")
@EntityListeners(AuditingEntityListener.class)
public class Team {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private TeamName teamName;

    @Enumerated(EnumType.STRING)
    @Column(name = "team_type", nullable = false, length = 20)
    private TeamType teamType;

    @Embedded
    private UniversityName universityName;

    @Embedded
    private Description description;

    @Embedded
    private TimeStamp timeStamp = new TimeStamp();

    protected Team() { }

    protected Team(String teamName, String university, TeamType teamType, String description) {
        this.teamName = TeamName.of(teamName);
        this.universityName = UniversityName.of(university);
        this.teamType = teamType;
        this.description = Description.of(description);
    }

    public static Team of(String teamName, String university, TeamType teamType, String description) {
        return new Team(teamName, university, teamType, description);
    }

    public Long getId() {
        return id;
    }

    public TeamName getTeamName() {
        return teamName;
    }

    public TeamType getTeamType() {
        return teamType;
    }

    public UniversityName getUniversityName() {
        return universityName;
    }

    public Description getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return timeStamp.getCreatedAt();
    }

    public void changeTeamInfo(String name, String university, String description) {
        this.teamName = TeamName.of(name);
        this.universityName = UniversityName.of(university);
        this.description = Description.of(description);
    }
}
