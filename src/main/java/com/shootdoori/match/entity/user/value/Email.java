package com.shootdoori.match.entity.user.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Email {
    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String value;

    protected Email() {}

    private Email(String value) {
        validate(value);
        this.value = value;
    }

    public static Email of(String value) {
        return new Email(value);
    }

    private void validate(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("이메일은 필수 입력 값입니다.");
        }
        if (email.length() > 255) {
            throw new IllegalArgumentException("이메일은 255자를 초과할 수 없습니다.");
        }
        if (!email.matches("^[a-zA-Z0-9._%+-]+@([a-zA-Z0-9-]+\\.)*ac\\.kr$")) {
            throw new IllegalArgumentException("학교 이메일은 'ac.kr' 도메인이어야 합니다.");
        }
    }

    public String value() {
        return value;
    }
}
