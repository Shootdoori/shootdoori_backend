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
    LocalDateTime expiryDate;

    protected RefreshToken() {
    }

    private RefreshToken(String id, User user, DeviceType deviceType, String userAgent,
        LocalDateTime expiryDate) {
        this.id = id;
        this.user = user;
        this.deviceType = deviceType;
        this.userAgent = userAgent;
        this.expiryDate = expiryDate;
    }


    public static RefreshToken of(String id, User user, DeviceType deviceType, String userAgent,
        LocalDateTime expiryDate) {
        return new RefreshToken(id, user, deviceType, userAgent, expiryDate);
    }
}
