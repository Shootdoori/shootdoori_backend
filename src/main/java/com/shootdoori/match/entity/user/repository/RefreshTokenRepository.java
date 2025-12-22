package com.shootdoori.match.entity.user.repository;

import com.shootdoori.match.entity.user.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    
}
