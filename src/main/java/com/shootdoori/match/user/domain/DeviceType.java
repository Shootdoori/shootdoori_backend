package com.shootdoori.match.user.domain;

import java.util.List;
import java.util.stream.Stream;

public enum DeviceType {
    ANDROID("안드로이드", List.of("android")),
    IOS("아이폰", List.of("iphone", "ipad", "ios")),
    WEB("웹", List.of("windows", "mac", "linux")),
    UNKNOWN("그 외", List.of());

    private final String type;
    private final List<String> keywords;

    DeviceType(String type, List<String> keywords) {
        this.type = type;
        this.keywords = keywords;
    }

    public static DeviceType fromUserAgent(String userAgent) {
        if (userAgent == null) {
            return DeviceType.UNKNOWN;
        }

        String lowerUserAgent = userAgent.toLowerCase();

        return Stream.of(values())
            .filter(type -> type.keywords.stream()
                .anyMatch(keyword -> lowerUserAgent.contains(keyword)))
            .findFirst()
            .orElse(DeviceType.UNKNOWN);
    }
}
