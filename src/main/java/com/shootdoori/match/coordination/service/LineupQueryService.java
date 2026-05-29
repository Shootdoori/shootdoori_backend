package com.shootdoori.match.coordination.service;

import com.shootdoori.match.coordination.domain.Lineup;
import com.shootdoori.match.coordination.domain.LineupMember;
import com.shootdoori.match.coordination.repository.LineupRepository;
import com.shootdoori.match.dto.LineupMemberResponseDto;
import com.shootdoori.match.exception.common.ErrorCode;
import com.shootdoori.match.exception.common.NotFoundException;
import com.shootdoori.match.team.domain.TeamMember;
import com.shootdoori.match.team.service.TeamMemberQueryService;
import com.shootdoori.match.user.domain.User;
import com.shootdoori.match.user.service.UserQueryService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class LineupQueryService {

    private final TeamMemberQueryService teamMemberQueryService;
    private final LineupRepository lineupRepository;
    private final UserQueryService userQueryService;

    public LineupQueryService(
        TeamMemberQueryService teamMemberQueryService,
        LineupRepository lineupRepository,
        UserQueryService userQueryService
    ) {
        this.teamMemberQueryService = teamMemberQueryService;
        this.lineupRepository = lineupRepository;
        this.userQueryService = userQueryService;
    }

    public List<LineupMemberResponseDto> findById(Long loginUserId, Long lineupId) {
        Long loginTeamId = teamMemberQueryService.getTeamIdByUserId(loginUserId);
        Lineup lineup = lineupRepository.findByIdAndTeamId(lineupId, loginTeamId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.LINEUP_NOT_FOUND));
            
        return LineupMemberResponseDto.fromLineup(lineup, teamMemberQueryService, userQueryService);
    }
    
}
