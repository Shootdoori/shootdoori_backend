package com.shootdoori.match.controller;

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

    @PostMapping()
    public ResponseEntity<List<LineupMemberResponseDto>> create(@RequestBody List<LineupMemberRequestDto> requestDtos,
                                                                      @LoginUser Long userId) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<LineupMemberResponseDto>> getById(@PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
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
