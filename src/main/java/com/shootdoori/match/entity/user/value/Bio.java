package com.shootdoori.match.entity.user.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Bio {

    @Column(name = "bio",length = 500)
    private String value;

    protected Bio() {}

    private Bio(String value) {
        validate(value);
        this.value = value;
    }

    public static Bio of(String value) {
        return new Bio(value);
    }

    private void validate(String bio) {
        if (bio != null && bio.length() > 500) {
            throw new IllegalArgumentException("자기소개는 500자를 초과할 수 없습니다.");
        }
    }

    public String value() {
        return value;
    }
}
