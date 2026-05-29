package com.shootdoori.match.coordination.domain;

import com.shootdoori.match.entity.common.Position;
import com.shootdoori.match.entity.common.TimeStamp;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "lineup_member")
public class LineupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lineup_members_id", nullable = false)
    private LineupMembers lineupMembers;

    @Column(name = "team_member_id", nullable = false)
    private Long teamMemberId;

    @Enumerated(EnumType.STRING)
    @Column(name = "position", nullable = false)
    private Position position;

    @Column(name = "is_starter", nullable = false)
    private boolean isStarter;

    @Embedded
    private TimeStamp timeStamp = new TimeStamp();

    protected LineupMember() {
    }

    public LineupMember(LineupMembers lineupMembers, Long teamMemberId, Position position,
        boolean isStarter) {
        validateRequiredFields(lineupMembers, teamMemberId, position);

        this.lineupMembers = lineupMembers;
        this.teamMemberId = teamMemberId;
        this.position = position;
        this.isStarter = isStarter;
    }

    public Long getTeamMemberId() {
        return teamMemberId;
    }

    public Long getId() {
        return id;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isStarter() {
        return isStarter;
    }

    public LocalDateTime getCreatedAt() {
        return timeStamp.getCreatedAt();
    }

    public LocalDateTime getUpdatedAt() {
        return timeStamp.getUpdatedAt();
    }

    public boolean isSameTeamMember(Long teamMemberId) {
        return Objects.equals(this.teamMemberId, teamMemberId);
    }

    private void validateRequiredFields(LineupMembers lineupMembers, Long teamMemberId,
        Position position) {
        if (lineupMembers == null) {
            throw new IllegalArgumentException("lineupMembers는 필수입니다.");
        }
        if (teamMemberId == null) {
            throw new IllegalArgumentException("teamMemberId는 필수입니다.");
        }
        if (position == null) {
            throw new IllegalArgumentException("position은 필수입니다.");
        }
    }
}
