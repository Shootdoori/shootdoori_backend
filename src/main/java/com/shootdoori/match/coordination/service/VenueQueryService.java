package com.shootdoori.match.coordination.service;

import com.shootdoori.match.coordination.domain.Venue;
import com.shootdoori.match.coordination.repository.VenueRepository;
import com.shootdoori.match.dto.VenueSearchResponseDto;
import com.shootdoori.match.exception.common.ErrorCode;
import com.shootdoori.match.exception.common.NotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class VenueQueryService {

    private final VenueRepository venueRepository;

    public VenueQueryService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public Slice<VenueSearchResponseDto> findAll(Pageable pageable) {
        Slice<Venue> venues = venueRepository.findAll(pageable);

        return venues.map(v -> new VenueSearchResponseDto(
            v.getId(),
            v.getName(),
            v.getAddress(),
            v.getContactInfo(),
            v.getFacilities(),
            v.getPricePerHour()
        ));
    }

    public Venue findByIdForEntity(Long id) {
        return venueRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(ErrorCode.VENUE_NOT_FOUND,
                String.valueOf(id)));
    }
}

