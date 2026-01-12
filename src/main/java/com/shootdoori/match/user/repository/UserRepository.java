package com.shootdoori.match.user.repository;

import com.shootdoori.match.user.domain.User;
import com.shootdoori.match.user.domain.value.Email;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(Email email);

    Optional<User> findByEmail(Email email);
}