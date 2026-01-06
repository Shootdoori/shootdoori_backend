package com.shootdoori.match.user.repository;

import com.shootdoori.match.user.domain.RefreshToken;
import com.shootdoori.match.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    void deleteByUser(User user);
}
