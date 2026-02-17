package com.shootdoori.match.coordination.controller;

import com.shootdoori.match.coordination.service.VenueQueryService;
import com.shootdoori.match.dto.VenueSearchResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/venues")
public class VenueController {

    private final VenueQueryService venueQueryService;

    public VenueController(VenueQueryService venueQueryService) {
        this.venueQueryService = venueQueryService;
    }

    @GetMapping
    public Slice<VenueSearchResponseDto> findAll(
        @PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        return venueQueryService.findAll(pageable);
    }
}
