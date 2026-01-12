package com.shootdoori.match.joinWaiting.domain;

import com.shootdoori.match.entity.common.TimeStamp;
import com.shootdoori.match.exception.common.NoPermissionException;
import com.shootdoori.match.exception.domain.joinwaiting.JoinWaitingNotPendingException;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(
    name = "join_waiting",
    indexes = {
        @Index(name = "idx_join_waiting_team_status", columnList = "team_id, status")
    }
)
@EntityListeners(AuditingEntityListener.class)
public class JoinWaiting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "applicant_id")
    private Long applicantId;

    @Column(name = "processor_id")
    private Long processorId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private JoinWaitingStatus status = JoinWaitingStatus.PENDING;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "decision_reason", columnDefinition = "TEXT")
    private String decisionReason;

    @Enumerated(EnumType.STRING)
    @Column(name = "join_waiting_type", nullable = false, length = 20)
    private JoinWaitingType joinWaitingType = JoinWaitingType.MEMBER;

    @Embedded
    private TimeStamp timeStamp = new TimeStamp();

    protected JoinWaiting() {
    }

    public JoinWaiting(Long teamId, Long applicantId, String message,
        JoinWaitingType joinWaitingType) {
        this.teamId = teamId;
        this.applicantId = applicantId;
        this.message = message;
        this.joinWaitingType = joinWaitingType;
    }

    public Long getId() {
        return id;
    }

    public Long getTeamId() {
        return teamId;
    }

    public Long getApplicantId() {
        return applicantId;
    }

    public Long getProcessorId() {
        return processorId;
    }

    public JoinWaitingStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getDecisionReason() {
        return decisionReason;
    }

    public JoinWaitingType getJoinWaitingType() {
        return joinWaitingType;
    }

    public LocalDateTime getUpdatedAt() {
        return timeStamp.getUpdatedAt();
    }

    public void approve(Long loginUserId, String decisionReason) {
        validatePending();
        this.status = JoinWaitingStatus.APPROVED;
        this.processorId = loginUserId;
        this.decisionReason = decisionReason;
    }

    public void reject(Long loginUserId, String decisionReason) {
        validatePending();
        this.status = JoinWaitingStatus.REJECTED;
        this.processorId = loginUserId;
        this.decisionReason = decisionReason;
    }

    public void cancel(Long loginUserId, String decisionReason) {
        validatePending();
        this.status = JoinWaitingStatus.CANCELED;
        this.processorId = loginUserId;
        this.decisionReason = decisionReason;
    }

    public void validateApplicant(Long loginUserId) {
        if (!applicantId.equals(loginUserId)) {
            throw new NoPermissionException();
        }
    }

    private void validatePending() {
        if (!status.isPending()) {
            throw new JoinWaitingNotPendingException();
        }
    }
}