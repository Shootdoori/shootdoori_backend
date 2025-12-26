package com.shootdoori.match.user.domain.value;

import com.shootdoori.match.policy.PasswordPolicy;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Password {
    @Column(name = "password", nullable = false, length = 255)
    private String value;

    protected Password() {}

    private Password(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("인코딩된 패스워드는 비어 있을 수 없습니다.");
        }
        this.value = value;
    }

    public static Password of(String encodedPassword) {
        return new Password(encodedPassword);
    }

    public static void validateRaw(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("패스워드는 필수 입력 값입니다.");
        }

        if (raw.length() < PasswordPolicy.MIN_LENGTH || raw.length() > PasswordPolicy.MAX_LENGTH) {
            throw new IllegalArgumentException("비밀번호는 "
                + PasswordPolicy.MIN_LENGTH + "~" + PasswordPolicy.MAX_LENGTH + "자여야 합니다.");
        }

        if (!raw.matches(PasswordPolicy.REGEXP)) {
            throw new IllegalArgumentException(PasswordPolicy.MESSAGE);
        }
    }

    public String value() {
        return this.value;
    }
}
