package com.shootdoori.match.coordination.domain;

import com.shootdoori.match.entity.common.Position;
import com.shootdoori.match.entity.common.TimeStamp;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "lineup")
public class Lineup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "match_id", nullable = false)
    private Long matchId;
    
    @Column(name = "team_id", nullable = false) 
    private Long teamId;
    
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    @JoinColumn(name = "lineup_members_id", nullable = false, unique = true)
    private LineupMembers members;
    
    @Embedded
    private TimeStamp timeStamp = new TimeStamp();

    protected Lineup() {
    }

    public Lineup(Long matchId, Long teamId) {
        this.matchId = matchId;
        this.teamId = teamId;
        this.members = new LineupMembers();
    }

    public void addMember(Long teamMemberId, Position position, boolean isStarter) {
        members.addMember(teamMemberId, position, isStarter);
    }

    public void removeMember(Long teamMemberId) {
        members.removeMember(teamMemberId);
    }

    public boolean isValidLineup() {
        return members.isValidFinalLineup();
    }

    public int getTotalMemberCount() {
        return members.getTotalCount();
    }

    public LineupMembers getMembers() {
        return members;
    }

    public Long getId() {
        return id;
    }

    public Long getMatchId() {
        return matchId;
    }

    public Long getTeamId() { 
        return teamId; 
    }
}
