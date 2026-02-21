package com.shootdoori.match.coordination.repository;

import static com.shootdoori.match.coordination.domain.QMatch.match;
import static com.shootdoori.match.coordination.domain.QMatchApplication.matchApplication;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shootdoori.match.coordination.domain.MatchApplicationStatus;
import com.shootdoori.match.dto.MatchApplicationResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@Repository
public class MatchApplicationRepositoryQuerydslImpl implements MatchApplicationRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    public MatchApplicationRepositoryQuerydslImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Slice<MatchApplicationResponseDto> findReceivedPendingByHomeTeamId(Long homeTeamId,
        Pageable pageable) {
        List<MatchApplicationResponseDto> content = fetchMatchApplications(
            pageable,
            List.of(
                match.homeTeamId.eq(homeTeamId),
                matchApplication.status.eq(MatchApplicationStatus.PENDING)
            )
        );
        return toSlice(content, pageable);
    }

    @Override
    public Slice<MatchApplicationResponseDto> findSentByRequestTeamId(Long requestTeamId,
        Pageable pageable) {
        List<MatchApplicationResponseDto> content = fetchMatchApplications(
            pageable,
            List.of(matchApplication.requestTeamId.eq(requestTeamId))
        );
        return toSlice(content, pageable);
    }

    private List<MatchApplicationResponseDto> fetchMatchApplications(Pageable pageable,
        List<Predicate> predicates) {
        return queryFactory
            .select(Projections.constructor(
                MatchApplicationResponseDto.class,
                matchApplication.id,
                matchApplication.requestTeamId,
                match.homeTeamId,
                matchApplication.requestMessage,
                matchApplication.lineupId
            ))
            .from(matchApplication)
            .join(match).on(match.id.eq(matchApplication.matchId))
            .where(predicates.toArray(Predicate[]::new))
            .orderBy(matchApplication.timeStamp.createdAt.desc(), matchApplication.id.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize() + 1L)
            .fetch();
    }

    private Slice<MatchApplicationResponseDto> toSlice(List<MatchApplicationResponseDto> content,
        Pageable pageable) {
        boolean hasNext = content.size() > pageable.getPageSize();
        if (hasNext) {
            content.remove(pageable.getPageSize());
        }
        return new SliceImpl<>(content, pageable, hasNext);
    }
}
