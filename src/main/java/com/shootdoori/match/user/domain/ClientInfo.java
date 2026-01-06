package com.shootdoori.match.user.domain;

public class ClientInfo {

    private final String userAgent;
    private final DeviceType deviceType;

    public ClientInfo(String userAgent, DeviceType deviceType) {
        this.userAgent = userAgent;
        this.deviceType = deviceType;
    }
}
