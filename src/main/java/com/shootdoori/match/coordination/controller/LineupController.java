package com.shootdoori.match.coordination.controller;

import com.shootdoori.match.coordination.service.LineupCommandService;
import com.shootdoori.match.coordination.service.LineupQueryService;
import com.shootdoori.match.dto.LineupMemberRequestDto;
import com.shootdoori.match.dto.LineupMemberResponseDto;
import com.shootdoori.match.resolver.LoginUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping()
    public ResponseEntity<List<LineupMemberResponseDto>> create(@RequestBody List<LineupMemberRequestDto> requestDtos,
                                                                      @LoginUser Long userId) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<LineupMemberResponseDto>> findById(
        @LoginUser Long loginUserId,
        @PathVariable Long lineupId
    ) {
        return new ResponseEntity<>(
            lineupQueryService.findById(loginUserId, id),
            HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<List<LineupMemberResponseDto>> update(@PathVariable Long id,
                                                                @RequestBody List<LineupMemberRequestDto> requestDtos,
                                                                @LoginUser Long userId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                             @LoginUser Long userId) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
