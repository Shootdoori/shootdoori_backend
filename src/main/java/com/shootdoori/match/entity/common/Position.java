package com.shootdoori.match.entity.common;

public enum Position {

    GK,
    DF,
    MF,
    FW,

    CB,
    LB,
    RB,

    DM,
    CM,
    AM,

    ST,
    LW,
    RW;

    public static Position fromCode(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("포지션 값은 비어 있을 수 없습니다.");
        }

        for (Position pos : values()) {
            if (pos.name().equalsIgnoreCase(code)) {
                return pos;
            }
        }

        throw new IllegalArgumentException("잘못된 포지션 코드입니다: " + code);
    }
}