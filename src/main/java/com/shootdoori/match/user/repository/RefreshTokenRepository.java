package com.shootdoori.match.user.repository;

import com.shootdoori.match.user.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    
}
