package com.shootdoori.match.coordination.service;

import com.shootdoori.match.coordination.domain.Match;
import com.shootdoori.match.coordination.domain.MatchStatus;
import com.shootdoori.match.coordination.repository.MatchRepository;
import com.shootdoori.match.dto.RecentMatchesResponseDto;
import com.shootdoori.match.team.service.TeamMemberQueryService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TeamMatchQueryService {

    private final TeamMemberQueryService teamMemberQueryService;
    private final MatchRepository matchRepository;

    public TeamMatchQueryService(
        TeamMemberQueryService teamMemberQueryService,
        MatchRepository matchRepository
    ) {
        this.teamMemberQueryService = teamMemberQueryService;
        this.matchRepository = matchRepository;
    }

    public List<RecentMatchesResponseDto> findRecentCompletedMatches(
        Long loginUserId,
        LocalDate cursorDate,
        LocalTime cursorTime,
        int size
    ) {
        Long teamId = teamMemberQueryService.getTeamIdByUserId(loginUserId);
        LocalDateTime cursor = createCursor(cursorDate, cursorTime);
        int fetchSize = Math.min(size, 10);
        Pageable pageable = PageRequest.of(0, fetchSize);

        return matchRepository.findRecentFinishedMatchesByTeamId(
                teamId,
                MatchStatus.FINISHED,
                cursor,
                pageable
            )
            .stream()
            .map(RecentMatchesResponseDto::from)
            .toList();
    }

    private LocalDateTime createCursor(LocalDate cursorDate, LocalTime cursorTime) {
        if (cursorDate == null || cursorTime == null) {
            return LocalDateTime.MAX;
        }
        return LocalDateTime.of(cursorDate, cursorTime);
    }
}
