package com.shootdoori.match.coordination.repository;

import com.shootdoori.match.coordination.domain.Lineup;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineupRepository extends JpaRepository<Lineup, Long> {

    Optional<Lineup> findByIdAndTeamId(Long lineupId, Long teamId);
}
