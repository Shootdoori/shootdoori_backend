package com.shootdoori.match.coordination.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "venue")
@EntityListeners(AuditingEntityListener.class)
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @Column(name = "latitude", nullable = false, precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = false, precision = 11, scale = 8)
    private BigDecimal longitude;

    @Column(name = "contact_info", length = 100)
    private String contactInfo;

    @Column(name = "facilities", columnDefinition = "TEXT")
    private String facilities;

    @Column(name = "price_per_hour")
    private Long pricePerHour;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public String getFacilities() {
        return facilities;
    }

    public Long getPricePerHour() {
        return pricePerHour;
    }
}
