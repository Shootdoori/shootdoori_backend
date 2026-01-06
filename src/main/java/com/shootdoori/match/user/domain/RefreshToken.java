package com.shootdoori.match.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_token")
public class RefreshToken {

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    DeviceType deviceType;

    @Column
    String userAgent;

    @Column
    boolean revoked = false;

    @Column
    LocalDateTime expiryDate;

    @Version
    private Long version;

    protected RefreshToken() {
    }

    public RefreshToken(String id, User user, DeviceType deviceType, String userAgent,
        LocalDateTime expiryDate) {
        this.id = id;
        this.user = user;
        this.deviceType = deviceType;
        this.userAgent = userAgent;
        this.expiryDate = expiryDate;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void revoke() {
        revoked = true;
    }
}
