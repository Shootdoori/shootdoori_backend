package com.shootdoori.match.joinWaiting.repository;

import com.shootdoori.match.joinWaiting.domain.JoinWaiting;
import com.shootdoori.match.joinWaiting.domain.JoinWaitingStatus;
import com.shootdoori.match.joinWaiting.domain.JoinWaitingType;
import com.shootdoori.match.joinWaiting.dto.JoinWaitingResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JoinWaitingRepository extends JpaRepository<JoinWaiting, Long> {

    Page<JoinWaiting> findByTeamIdAndStatusAndJoinWaitingType(Long teamId, JoinWaitingStatus status,
        JoinWaitingType joinWaitingType, Pageable pageable);

    Page<JoinWaiting> findAllByApplicantIdAndJoinWaitingType(Long applicantId,
        JoinWaitingType joinWaitingType, Pageable pageable);
}
