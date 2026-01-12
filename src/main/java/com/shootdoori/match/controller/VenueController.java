package com.shootdoori.match.controller;

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

    @GetMapping
    public Slice<VenueSearchResponseDto> getAllVenues(
        @PageableDefault(sort = "venueName", direction = Sort.Direction.ASC) Pageable pageable) {
        return null;
    }
}
