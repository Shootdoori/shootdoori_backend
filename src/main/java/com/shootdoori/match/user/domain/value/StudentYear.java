package com.shootdoori.match.user.domain.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class StudentYear {
    @Column(name = "student_year", nullable = false, length = 2)
    private String value;

    protected StudentYear() {}

    private StudentYear(String value) {
        validate(value);
        this.value = value;
    }

    public static StudentYear of(String value) {
        return new StudentYear(value);
    }

    private void validate(String year) {
        if (year == null || year.isBlank()) {
            throw new IllegalArgumentException("입학년도는 필수 입력 값입니다.");
        }
        if (!year.matches("\\d{2}")) {
            throw new IllegalArgumentException(
                "입학년도는 2자리 숫자로 입력해주세요. (예: 25)"
            );
        }
    }

    public String value() {
        return value;
    }
}
