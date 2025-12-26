package com.shootdoori.match.team.domain.value;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Description {

    private static final int MAX_DESCRIPTION_LENGTH = 1000;

    private String description;

    protected Description() { }

    public Description(String description) {
        if (description != null && description.isBlank()) {
            description = null;
        }

        validate(description);
        this.description = description;
    }

    public static Description of(String description) {
        return new Description(description);
    }

    public static Description empty() {
        return new Description(null);
    }

    private void validate(String description) {
        if (description != null && description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException("설명은 최대 1000자입니다.");
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Description that = (Description) o;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(description);
    }
}