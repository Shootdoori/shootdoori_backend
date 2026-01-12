package com.shootdoori.match.user.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class DeviceTypeTest {

    @ParameterizedTest
    @ValueSource(strings = {"android", "Android", "ANDROID", "Mozilla/5.0 (Linux; Android 10...)"})
    @DisplayName("User-Agent에 android가 포함되면 ANDROID 타입을 반환")
    void fromUserAgent_Android(String userAgent) {
        assertThat(DeviceType.fromUserAgent(userAgent)).isEqualTo(DeviceType.ANDROID);
    }

    @ParameterizedTest
    @ValueSource(strings = {"iphone", "iPhone", "iPad", "Mozilla/5.0 (iPhone; CPU iPhone OS...)"})
    @DisplayName("User-Agent에 iphone 또는 ipad가 포함되면 IOS 타입을 반환")
    void fromUserAgent_IOS(String userAgent) {
        assertThat(DeviceType.fromUserAgent(userAgent)).isEqualTo(DeviceType.IOS);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "windows", "Windows NT 10.0",
        "macintosh", "Mac OS X 10_15_7",
        "linux", "X11; Linux x86_64"
    })
    @DisplayName("User-Agent에 windows, mac, linux가 포함되면 WEB 타입을 반환")
    void fromUserAgent_Web(String userAgent) {
        assertThat(DeviceType.fromUserAgent(userAgent)).isEqualTo(DeviceType.WEB);
    }

    @Test
    @DisplayName("알 수 없는 User-Agent는 UNKNOWN 타입을 반환한다")
    void fromUserAgent_Unknown() {
        String userAgent = "UnknownAgent";
        assertThat(DeviceType.fromUserAgent(userAgent)).isEqualTo(DeviceType.UNKNOWN);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("User-Agent가 null 또는 빈 문자열이면 UNKNOWN 타입을 반환한다")
    void fromUserAgent_Null_Or_Empty(String userAgent) {
        assertThat(DeviceType.fromUserAgent(userAgent)).isEqualTo(DeviceType.UNKNOWN);
    }
}
