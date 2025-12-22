package com.shootdoori.match.entity.user.repository;

import com.shootdoori.match.entity.user.User;
import com.shootdoori.match.entity.user.value.Email;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(Email email);

    Optional<User> findByEmail(Email of);
}