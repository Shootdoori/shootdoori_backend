package com.shootdoori.match.coordination.service;

import com.shootdoori.match.coordination.domain.Lineup;
import com.shootdoori.match.coordination.domain.LineupMember;
import com.shootdoori.match.coordination.repository.LineupRepository;
import com.shootdoori.match.dto.LineupMemberRequestDto;
import com.shootdoori.match.dto.LineupMemberResponseDto;
import com.shootdoori.match.exception.common.CreationFailException;
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
@Transactional
public class LineupCommandService {

    private final TeamMemberQueryService teamMemberQueryService;
    private final LineupQueryService lineupQueryService;
    private final LineupRepository lineupRepository;
    private final UserQueryService userQueryService;

    public LineupCommandService(
        TeamMemberQueryService teamMemberQueryService, LineupQueryService lineupQueryService,
        LineupRepository lineupRepository, UserQueryService userQueryService
    ) {
        this.teamMemberQueryService = teamMemberQueryService;
        this.lineupQueryService = lineupQueryService;
        this.lineupRepository = lineupRepository;
        this.userQueryService = userQueryService;
    }

    public List<LineupMemberResponseDto> create(Long loginUserId, Long matchId,
        List<LineupMemberRequestDto> requestDtos) {
        Long loginTeamId = teamMemberQueryService.getTeamIdByUserId(loginUserId);

        Lineup lineup = new Lineup(matchId, loginTeamId);
        addMembers(lineup, loginTeamId, requestDtos);
        validateLineup(lineup);

        Lineup saved = lineupRepository.save(lineup);
        return LineupMemberResponseDto.fromLineup(saved, teamMemberQueryService, userQueryService);
    }

    public List<LineupMemberResponseDto> update(Long loginUserId, Long lineupId,
        List<LineupMemberRequestDto> requestDtos) {
        Long loginTeamId = teamMemberQueryService.getTeamIdByUserId(loginUserId);
        Lineup lineup = findOwnedLineup(loginTeamId, lineupId);

        clearMembers(lineup);
        addMembers(lineup, loginTeamId, requestDtos);
        validateLineup(lineup);

        return LineupMemberResponseDto.fromLineup(lineup, teamMemberQueryService, userQueryService);
    }

    public void delete(Long loginUserId, Long lineupId) {
        Long loginTeamId = teamMemberQueryService.getTeamIdByUserId(loginUserId);
        Lineup lineup = findOwnedLineup(loginTeamId, lineupId);
        lineupRepository.delete(lineup);
    }

    private void addMembers(Lineup lineup, Long loginTeamId, List<LineupMemberRequestDto> requestDtos) {
        for (LineupMemberRequestDto requestDto : requestDtos) {
            TeamMember teamMember = teamMemberQueryService.findByIdForEntity(requestDto.teamMemberId());
            teamMember.validateBelongsToTeam(loginTeamId);

            User user = userQueryService.findByIdForEntity(teamMember.getUserId());
            lineup.addMember(teamMember.getId(), user.getPosition(), Boolean.TRUE.equals(requestDto.isStarter()));
        }
    }

    private void clearMembers(Lineup lineup) {
        List<Long> memberIds = new ArrayList<>();
        for (LineupMember member : lineup.getMembers().getMembers()) {
            memberIds.add(member.getTeamMemberId());
        }

        for (Long teamMemberId : memberIds) {
            lineup.removeMember(teamMemberId);
        }
    }

    private void validateLineup(Lineup lineup) {
        if (!lineup.isValidLineup()) {
            throw new CreationFailException(ErrorCode.LINEUP_CREATION_FAILED);
        }
    }


    private Lineup findOwnedLineup(Long teamId, Long lineupId) {
        return lineupRepository.findByIdAndTeamId(lineupId, teamId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.LINEUP_NOT_FOUND));
    }

}
