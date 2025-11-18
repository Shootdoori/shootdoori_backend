package com.shootdoori.match.entity.team;

public record UniversityName(String universityName) {

    private static final int MAX_UNIVERSITY_NAME = 100;

    public UniversityName {
        if (universityName.length() > MAX_UNIVERSITY_NAME) {
            throw new IllegalArgumentException("대학교명은 최대 100자입니다.");
        }
    }

    public static UniversityName of(String name) {
        return new UniversityName(name);
    }

    @Override
    public int hashCode() {
        return universityName.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        UniversityName that = (UniversityName) other;
        return this.universityName.equals(that.universityName());
    }
}
