package com.shootdoori.match.entity.user.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Department {

    @Column(name = "department", nullable = false, length = 100)
    private String value;

    protected Department() {}

    private Department(String value) {
        validate(value);
        this.value = value;
    }

    public static Department of(String value) {
        return new Department(value);
    }

    private void validate(String dept) {
        if (dept == null || dept.isBlank()) {
            throw new IllegalArgumentException("학과 이름은 필수 입력 값입니다.");
        }
        if (dept.length() > 100) {
            throw new IllegalArgumentException("학과 이름은 100자를 초과할 수 없습니다.");
        }
    }

    public String value() {
        return value;
    }
}
