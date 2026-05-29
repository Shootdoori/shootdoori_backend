package com.shootdoori.match.coordination.controller;

import com.shootdoori.match.coordination.service.LineupCommandService;
import com.shootdoori.match.coordination.service.LineupQueryService;
import com.shootdoori.match.dto.LineupCreateRequestDto;
import com.shootdoori.match.dto.LineupMemberRequestDto;
import com.shootdoori.match.dto.LineupMemberResponseDto;
import com.shootdoori.match.resolver.LoginUser;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lineups")
public class LineupController {

    private final LineupCommandService lineupCommandService;
    private final LineupQueryService lineupQueryService;

    public LineupController(
        LineupCommandService lineupCommandService,
        LineupQueryService lineupQueryService
    ) {
        this.lineupCommandService = lineupCommandService;
        this.lineupQueryService = lineupQueryService;
    }

    @PostMapping
    public ResponseEntity<List<LineupMemberResponseDto>> create(
        @LoginUser Long loginUserId,
        @RequestBody LineupCreateRequestDto requestDto
    ) {
        return new ResponseEntity<>(
            lineupCommandService.create(loginUserId, requestDto.matchId(), requestDto.members()),
            HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<LineupMemberResponseDto>> findById(
        @LoginUser Long loginUserId,
        @PathVariable Long id
    ) {
        return new ResponseEntity<>(
            lineupQueryService.findById(loginUserId, id),
            HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<List<LineupMemberResponseDto>> update(
        @LoginUser Long loginUserId,
        @PathVariable Long id,
        @RequestBody List<LineupMemberRequestDto> requestDtos
    ) {
        return new ResponseEntity<>(
            lineupCommandService.update(loginUserId, id, requestDtos),
            HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @LoginUser Long loginUserId,
        @PathVariable Long id
    ) {
        lineupCommandService.delete(loginUserId, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}