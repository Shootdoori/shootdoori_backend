package com.shootdoori.match.team.repository;

import com.shootdoori.match.team.domain.Team;
import com.shootdoori.match.team.domain.value.UniversityName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Page<Team> findAllByUniversityName(UniversityName of, Pageable pageable);
}
