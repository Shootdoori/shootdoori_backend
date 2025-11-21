package com.shootdoori.match.entity.user;

import com.shootdoori.match.entity.user.value.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(Email email);
}