package com.shootdoori.match.joinWaiting.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.shootdoori.match.exception.common.NoPermissionException;
import com.shootdoori.match.joinWaiting.domain.JoinWaiting;
import com.shootdoori.match.joinWaiting.domain.JoinWaitingStatus;
import com.shootdoori.match.joinWaiting.domain.JoinWaitingType;
import com.shootdoori.match.joinWaiting.dto.JoinWaitingApproveRequestDto;
import com.shootdoori.match.joinWaiting.dto.JoinWaitingCancelRequestDto;
import com.shootdoori.match.joinWaiting.dto.JoinWaitingRejectRequestDto;
import com.shootdoori.match.joinWaiting.mapper.JoinWaitingMapper;
import com.shootdoori.match.joinWaiting.repository.JoinWaitingRepository;
import com.shootdoori.match.team.service.TeamMemberQueryService;
import com.shootdoori.match.team.service.TeamQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JoinWaitingCommandServiceTest {

    @Mock
    private TeamQueryService teamQueryService;

    @Mock
    private TeamMemberQueryService teamMemberQueryService;

    @Mock
    private JoinWaitingQueryService joinWaitingQueryService;

    @Mock
    private JoinWaitingRepository joinWaitingRepository;

    @Mock
    private JoinWaitingMapper joinWaitingMapper;

    private JoinWaitingCommandService joinWaitingCommandService;

    private final Long teamId = 1L;
    private final Long joinWaitingId = 1L;
    private final Long loginUserId = 1L;
    private final Long applicantId = 2L;

    @BeforeEach
    void setUp() {
        joinWaitingCommandService = new JoinWaitingCommandService(teamQueryService,
            teamMemberQueryService, joinWaitingQueryService, joinWaitingRepository,
            joinWaitingMapper);
    }

    @Test
    @DisplayName("가입 대기 승인 성공")
    void approve() {
        // given
        JoinWaitingApproveRequestDto requestDto = new JoinWaitingApproveRequestDto("일반멤버", "맘에 듭니다.");
        JoinWaiting joinWaiting = new JoinWaiting(teamId, applicantId, "가입신청", JoinWaitingType.MEMBER);

        given(joinWaitingQueryService.findByIdForEntity(joinWaitingId)).willReturn(joinWaiting);

        // when
        joinWaitingCommandService.approve(teamId, joinWaitingId, loginUserId, requestDto);

        // then
        assertThat(joinWaiting.getStatus()).isEqualTo(JoinWaitingStatus.APPROVED);
        verify(teamMemberQueryService, times(1)).validateLeaderOrViceLeader(teamId, loginUserId);
    }

    @Test
    @DisplayName("가입 대기 거절 성공")
    void reject() {
        // given
        JoinWaitingRejectRequestDto requestDto = new JoinWaitingRejectRequestDto("죄송합니다.");
        JoinWaiting joinWaiting = new JoinWaiting(teamId, applicantId, "가입신청", JoinWaitingType.MEMBER);

        given(joinWaitingQueryService.findByIdForEntity(joinWaitingId)).willReturn(joinWaiting);

        // when
        joinWaitingCommandService.reject(teamId, joinWaitingId, loginUserId, requestDto);

        // then
        assertThat(joinWaiting.getStatus()).isEqualTo(JoinWaitingStatus.REJECTED);
        verify(teamMemberQueryService, times(1)).validateLeaderOrViceLeader(teamId, loginUserId);
    }

    @Test
    @DisplayName("가입 대기 취소 성공")
    void cancel() {
        // given
        JoinWaitingCancelRequestDto requestDto = new JoinWaitingCancelRequestDto("개인 사정으로 취소합니다.");
        JoinWaiting joinWaiting = new JoinWaiting(teamId, applicantId, "가입신청", JoinWaitingType.MEMBER);

        given(joinWaitingQueryService.findByIdForEntity(joinWaitingId)).willReturn(joinWaiting);

        // when
        joinWaitingCommandService.cancel(teamId, joinWaitingId, applicantId, requestDto);

        // then
        assertThat(joinWaiting.getStatus()).isEqualTo(JoinWaitingStatus.CANCELED);
    }

    @Test
    @DisplayName("권한이 없는 사용자가 승인하면 예외 발생")
    void approve_fail_no_permission() {
        // given
        Long unauthorizedUserId = 999L;
        JoinWaitingApproveRequestDto requestDto = new JoinWaitingApproveRequestDto("일반멤버", "맘에 듭니다.");
        JoinWaiting joinWaiting = new JoinWaiting(teamId, applicantId, "가입신청", JoinWaitingType.MEMBER);

        given(joinWaitingQueryService.findByIdForEntity(joinWaitingId)).willReturn(joinWaiting);
        doThrow(new NoPermissionException()).when(teamMemberQueryService)
            .validateLeaderOrViceLeader(teamId, unauthorizedUserId);

        // when & then
        assertThatThrownBy(
            () -> joinWaitingCommandService.approve(teamId, joinWaitingId, unauthorizedUserId, requestDto))
            .isInstanceOf(NoPermissionException.class);
    }

    @Test
    @DisplayName("권한이 없는 사용자가 거절하면 예외 발생")
    void reject_fail_no_permission() {
        // given
        Long unauthorizedUserId = 999L;
        JoinWaitingRejectRequestDto requestDto = new JoinWaitingRejectRequestDto("맘에 듭니다.");
        JoinWaiting joinWaiting = new JoinWaiting(teamId, applicantId, "가입신청", JoinWaitingType.MEMBER);

        given(joinWaitingQueryService.findByIdForEntity(joinWaitingId)).willReturn(joinWaiting);
        doThrow(new NoPermissionException())
            .when(teamMemberQueryService).validateLeaderOrViceLeader(teamId, unauthorizedUserId);

        // when & then
        assertThatThrownBy(
            () -> joinWaitingCommandService.reject(teamId, joinWaitingId, unauthorizedUserId, requestDto))
            .isInstanceOf(NoPermissionException.class);
    }

    @Test
    @DisplayName("가입 요청 당사자가 아닌 유저가 취소하면 예외 발생")
    void cancel_fail_no_permission() {
        // given
        Long otherUserId = 999L;
        JoinWaitingCancelRequestDto requestDto = new JoinWaitingCancelRequestDto("개인 사정으로 취소합니다.");
        JoinWaiting joinWaiting = new JoinWaiting(teamId, applicantId, "가입신청", JoinWaitingType.MEMBER);

        given(joinWaitingQueryService.findByIdForEntity(joinWaitingId)).willReturn(joinWaiting);

        // when & then
        assertThatThrownBy(
            () -> joinWaitingCommandService.cancel(teamId, joinWaitingId, otherUserId, requestDto))
            .isInstanceOf(NoPermissionException.class);
    }
}
