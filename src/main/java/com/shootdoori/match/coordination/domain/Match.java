package com.shootdoori.match.coordination.domain;

import com.shootdoori.match.entity.common.TimeStamp;
import com.shootdoori.match.exception.common.ErrorCode;
import com.shootdoori.match.exception.common.NoPermissionException;
import com.shootdoori.match.exception.common.NotFoundException;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "match")
@EntityListeners(AuditingEntityListener.class)
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "home_team_id", nullable = false)
    private Long homeTeamId;

    @Column(name = "away_team_id")
    private Long awayTeamId;

    @Column(name = "home_lineup_id")
    private Long homeLineupId;

    @Column(name = "away_lineup_id")
    private Long awayLineupId;

    @Embedded
    private PreferredSchedule preferredSchedule;

    @Column(name = "match_at")
    private LocalDateTime matchAt;

    @Column(name = "venue_id", nullable = false)
    private Long venueId;

    @Column(name = "university_only", nullable = false)
    private boolean universityOnly;

    @Column(name = "message", length = 500)
    private String message;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MatchStatus status;

    @Embedded
    private TimeStamp timeStamp = new TimeStamp();

    @Version
    private Long version;

    protected Match() {
    }

    public Match(Long homeTeamId, LocalDate preferredDate, LocalTime preferredTimeStart,
        LocalTime preferredTimeEnd, Long venueId, boolean universityOnly, String message,
        Long homeLineupId) {
        this.homeTeamId = homeTeamId;
        this.preferredSchedule = new PreferredSchedule(preferredDate, preferredTimeStart,
            preferredTimeEnd);
        this.venueId = venueId;
        this.universityOnly = universityOnly;
        this.message = message;
        this.homeLineupId = homeLineupId;
        this.expiresAt = calculateExpiresAt(preferredDate);
        this.status = MatchStatus.WAITING;
    }

    private LocalDateTime calculateExpiresAt(LocalDate preferredDate) {
        return preferredDate.minusDays(1).atTime(23, 59, 59);
    }

    public void match(Long awayTeamId, Long awayLineupId, LocalDateTime matchAt) {
        status.validateMatchable();
        this.awayTeamId = awayTeamId;
        this.awayLineupId = awayLineupId;
        this.matchAt = matchAt;
        this.status = MatchStatus.MATCHED;
    }

    public void cancel() {
        status.validateCancelable();
        this.status = MatchStatus.CANCELED;
    }

    public void finish() {
        status.validateFinishable();
        this.status = MatchStatus.FINISHED;
    }

    public Long findEnemyTeamId(Long loginTeamId) {
        boolean home = homeTeamId.equals(loginTeamId);
        boolean away = awayTeamId != null && awayTeamId.equals(loginTeamId);

        if (!home && !away) {
            throw new NoPermissionException(ErrorCode.MATCH_OPERATION_PERMISSION_DENIED);
        }
        if (home && awayTeamId == null) {
            throw new NotFoundException(ErrorCode.TEAM_NOT_FOUND);
        }

        return !home ? homeTeamId : awayTeamId;
    }

    public void validateHomeTeam(Long loginTeamId) {
        if (!homeTeamId.equals(loginTeamId)) {
            throw new NoPermissionException(ErrorCode.MATCH_OPERATION_PERMISSION_DENIED);
        }
    }

    public void validateNotApplyingToOwnTeam(Long requestTeamId) {
        if (homeTeamId.equals(requestTeamId)) {
            throw new IllegalStateException("자기 팀에는 매치 신청할 수 없습니다.");
        }
    }

    public void validateWaitingStatus() {
        status.validateWaitingStatus();
    }

    public Long getId() {
        return id;
    }

    public Long getHomeTeamId() {
        return homeTeamId;
    }

    public Long getAwayTeamId() {
        return awayTeamId;
    }

    public Long getHomeLineupId() {
        return homeLineupId;
    }

    public Long getAwayLineupId() {
        return awayLineupId;
    }

    public LocalDate getPreferredDate() {
        return preferredSchedule.getPreferredDate();
    }

    public LocalTime getPreferredTimeStart() {
        return preferredSchedule.getPreferredTimeStart();
    }

    public LocalTime getPreferredTimeEnd() {
        return preferredSchedule.getPreferredTimeEnd();
    }

    public LocalDateTime getMatchAt() {
        return matchAt;
    }

    public Long getVenueId() {
        return venueId;
    }

    public boolean isUniversityOnly() {
        return universityOnly;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return timeStamp.getCreatedAt();
    }

    public LocalDateTime getUpdatedAt() {
        return timeStamp.getUpdatedAt();
    }
}

