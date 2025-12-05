package com.shootdoori.match.entity.common;

import java.util.Arrays;

public enum SkillLevel {

    PRO("프로"),
    SEMI_PRO("세미프로"),
    AMATEUR("아마추어");

    private final String displayName;

    SkillLevel(String displayName) {
        this.displayName = displayName;
    }

    public static SkillLevel fromDisplayName(String displayName) {
        if (displayName == null || displayName.isBlank()) {
            throw new IllegalArgumentException("스킬 레벨 이름은 비어 있을 수 없습니다.");
        }

        for (SkillLevel level : values()) {
            if (level.displayName.equals(displayName)) {
                return level;
            }
        }

        throw new IllegalArgumentException("잘못된 스킬 레벨 이름입니다: " + displayName);
    }

    public static SkillLevel fromCode(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("스킬 레벨 값은 비어 있을 수 없습니다.");
        }

        for (SkillLevel level : values()) {
            if (level.name().equalsIgnoreCase(code)) {
                return level;
            }
        }

        throw new IllegalArgumentException("잘못된 스킬 레벨 코드입니다: " + code);
    }
}
