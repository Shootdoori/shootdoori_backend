package com.shootdoori.match.user.domain.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class KakaoTalkId {
    @Column(name = "kakao_user_id", nullable = false, length = 20)
    private String value;

    protected KakaoTalkId() {}

    private KakaoTalkId(String value) {
        validate(value);
        this.value = value;
    }

    public static KakaoTalkId of(String value) {
        return new KakaoTalkId(value);
    }

    private void validate(String kakaoTalkId) {
        if (kakaoTalkId == null || kakaoTalkId.isBlank()) {
            throw new IllegalArgumentException("카카오톡 ID는 필수 입력 값입니다.");
        }

        if (!kakaoTalkId.matches("^[a-zA-Z0-9_.-]{4,20}$")) {
            throw new IllegalArgumentException(
                "ID는 4~20자의 영문, 숫자, 특수문자(-, _, .)만 사용 가능합니다."
            );
        }
    }

    public String value() {
        return value;
    }
}
