package com.shootdoori.match.coordination.domain;

import com.shootdoori.match.entity.common.Position;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lineup_members")
public class LineupMembers {

    private static final int MIN_TOTAL_COUNT = 11;
    private static final int MAX_TOTAL_COUNT = 18;
    private static final int MAX_STARTER_COUNT = 11;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "lineupMembers",
        cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
        orphanRemoval = true)
    private List<LineupMember> members = new ArrayList<>();

    protected LineupMembers() {
    }

    public void addMember(Long teamMemberId, Position position, boolean isStarter) {
        validateDuplicate(teamMemberId);
        validateMaxTotalCount();
        validateMaxStarterCount(isStarter);

        members.add(new LineupMember(this, teamMemberId, position, isStarter));
    }

    public void removeMember(Long teamMemberId) {
        members.removeIf(member -> member.isSameTeamMember(teamMemberId));
    }

    public int getTotalCount() {
        return members.size();
    }

    public boolean isValidFinalLineup() {
        int totalCount = getTotalCount();
        return totalCount >= MIN_TOTAL_COUNT && totalCount <= MAX_TOTAL_COUNT;
    }

    public List<LineupMember> getMembers() {
        return List.copyOf(members);
    }

    public boolean isEmpty() {
        return members.isEmpty();
    }

    private void validateDuplicate(Long teamMemberId) {
        boolean duplicated = members.stream()
            .anyMatch(member -> member.isSameTeamMember(teamMemberId));

        if (duplicated) {
            throw new IllegalArgumentException("이미 존재하는 팀 멤버입니다.");
        }
    }

    private void validateMaxTotalCount() {
        if (getTotalCount() >= MAX_TOTAL_COUNT) {
            throw new IllegalArgumentException("최종 라인업은 최대 18명까지 등록할 수 있습니다.");
        }
    }

    private void validateMaxStarterCount(boolean isStarter) {
        if (!isStarter) {
            return;
        }

        long starterCount = members.stream().filter(LineupMember::isStarter).count();
        if (starterCount >= MAX_STARTER_COUNT) {
            throw new IllegalArgumentException("선발 라인업은 최대 11명까지 등록할 수 있습니다.");
        }
    }
}
