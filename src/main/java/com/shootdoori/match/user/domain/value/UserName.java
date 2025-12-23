package com.shootdoori.match.user.domain.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UserName {

    @Column(name = "user_name", nullable = false, length = 100)
    private String value;

    protected UserName() {}

    private UserName(String value) {
        validate(value);
        this.value = value;
    }

    public static UserName of(String value) {
        return new UserName(value);
    }

    private void validate(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 필수 입력 값입니다.");
        }
        if (name.length() < 2 || name.length() > 100) {
            throw new IllegalArgumentException("이름은 2자 이상 100자 이하로 입력해주세요.");
        }
    }

    public String value() {
        return value;
    }
}
