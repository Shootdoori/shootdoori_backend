package com.shootdoori.match.repository;

import com.shootdoori.match.entity.team.Team;
import com.shootdoori.match.entity.team.UniversityName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Page<Team> findAllByUniversityName(UniversityName of, Pageable pageable);
}
