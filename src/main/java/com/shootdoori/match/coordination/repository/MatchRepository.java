package com.shootdoori.match.coordination.repository;

import com.shootdoori.match.coordination.domain.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {

}
