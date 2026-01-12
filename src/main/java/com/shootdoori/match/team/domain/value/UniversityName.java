package com.shootdoori.match.team.domain.value;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class UniversityName {

    private static final int MAX_UNIVERSITY_NAME = 100;

    private String universityName;

    protected UniversityName() {
    }

    private UniversityName(String universityName) {
        validate(universityName);
        this.universityName = universityName;
    }

    public static UniversityName of(String name) {
        return new UniversityName(name);
    }

    private void validate(String universityName) {
        if (universityName.length() > MAX_UNIVERSITY_NAME) {
            throw new IllegalArgumentException("대학교명은 최대 100자입니다.");
        }
    }

    public String getUniversityName() {
        return universityName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UniversityName that = (UniversityName) o;
        return Objects.equals(universityName, that.universityName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(universityName);
    }
}
