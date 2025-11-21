package com.shootdoori.match.team.repository;

import com.shootdoori.match.team.domain.TeamMember;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

    Optional<TeamMember> findByTeamIdAndUserId(Long teamId, Long userId);

    Page<TeamMember> findAllByTeamId(Long teamId, Pageable pageable);

    Optional<TeamMember> findByUserId(Long userId);

    @Query("SELECT tm FROM TeamMember tm " +
        "WHERE tm.team.id = :teamId " +
        "AND (:cursorId IS NULL OR tm.id > :cursorId) " +
        "ORDER BY tm.id ASC")
    Slice<TeamMember> findSliceByTeam_Id(
        @Param("teamId") Long teamId,
        @Param("cursorId") Long cursorId,
        Pageable pageable
    );
}
