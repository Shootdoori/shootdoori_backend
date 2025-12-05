package com.shootdoori.match.controller;

import com.shootdoori.match.dto.RecruitmentCreateRequest;
import com.shootdoori.match.dto.RecruitmentResponse;
import com.shootdoori.match.dto.RecruitmentUpdateRequest;
import com.shootdoori.match.resolver.LoginUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mercenaries/recruitments")
public class MercenaryRecruitmentController {
    @PostMapping
    public ResponseEntity<RecruitmentResponse> create(@RequestBody RecruitmentCreateRequest createRequest,
                                                      @LoginUser Long loginUserId) {
        return null;
    }

    @GetMapping
    public ResponseEntity<Page<RecruitmentResponse>> findAll(
        @PageableDefault(
            page = 0,size = 10,
            sort = {"matchDate", "matchTime"},
            direction = Sort.Direction.ASC
        ) Pageable pageable
    ) {
        return null;
    }

    @GetMapping("/me")
    public ResponseEntity<Page<RecruitmentResponse>> findAllForCaptain(
        @PageableDefault(
            page = 0,size = 10,
            sort = {"matchDate", "matchTime"},
            direction = Sort.Direction.ASC
        ) Pageable pageable,
        @LoginUser Long loginUserId
    ) {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecruitmentResponse> findById(@PathVariable Long id) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecruitmentResponse> update(
        @PathVariable Long id,
        @RequestBody RecruitmentUpdateRequest updateRequest,
        @LoginUser Long loginUserId
    ) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @LoginUser Long loginUserId) {
        return null;
    }
}
