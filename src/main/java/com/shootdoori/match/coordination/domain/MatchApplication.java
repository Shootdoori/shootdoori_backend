package com.shootdoori.match.coordination.domain;

import com.shootdoori.match.entity.common.TimeStamp;
import com.shootdoori.match.exception.common.ErrorCode;
import com.shootdoori.match.exception.common.NoPermissionException;
import com.shootdoori.match.exception.common.BusinessException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Embedded;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(
    name = "match_application",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_match_request_team_pending",
            columnNames = {"match_id", "request_team_id", "status"}
        )
    }
)
@EntityListeners(AuditingEntityListener.class)
public class MatchApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "match_id", nullable = false)
    private Long matchId;

    @Column(name = "request_team_id", nullable = false)
    private Long requestTeamId;

    @Column(name = "lineup_id", nullable = false)
    private Long lineupId;

    @Column(name = "request_message", columnDefinition = "TEXT")
    private String requestMessage;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MatchApplicationStatus status = MatchApplicationStatus.PENDING;

    @Column(name = "processed_by_team_id")
    private Long processedByTeamId;

    @Embedded
    private TimeStamp timeStamp = new TimeStamp();

    @Version
    private Long version;

    protected MatchApplication() {
    }

    public MatchApplication(Long matchId, Long requestTeamId, Long lineupId, String requestMessage) {
        this.matchId = matchId;
        this.requestTeamId = requestTeamId;
        this.lineupId = lineupId;
        this.requestMessage = requestMessage;
    }

    public Long getId() {
        return id;
    }

    public Long getMatchId() {
        return matchId;
    }

    public Long getRequestTeamId() {
        return requestTeamId;
    }

    public Long getLineupId() {
        return lineupId;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public MatchApplicationStatus getStatus() {
        return status;
    }

    public Long getProcessedByTeamId() {
        return processedByTeamId;
    }

    public LocalDateTime getCreatedAt() {
        return timeStamp.getCreatedAt();
    }

    public LocalDateTime getUpdatedAt() {
        return timeStamp.getUpdatedAt();
    }

    public void accept(Long processorTeamId) {
        status.validatePending();

        this.status = MatchApplicationStatus.ACCEPTED;
        this.processedByTeamId = processorTeamId;
    }

    public void reject(Long processorTeamId) {
       status.validatePending();

        this.status = MatchApplicationStatus.REJECTED;
        this.processedByTeamId = processorTeamId;
    }

    public void cancel(Long loginTeamId) {
        validateRequester(loginTeamId);
        status.validatePending();

        this.status = MatchApplicationStatus.CANCELED;
        this.processedByTeamId = loginTeamId;
    }

    public void validateRequester(Long loginTeamId) {
        if (!requestTeamId.equals(loginTeamId)) {
            throw new NoPermissionException(ErrorCode.MATCH_REQUEST_OWNERSHIP_VIOLATION);
        }
    }
}
