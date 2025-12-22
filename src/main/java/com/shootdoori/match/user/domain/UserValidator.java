package com.shootdoori.match.user.domain;

import com.shootdoori.match.user.repository.UserRepository;
import com.shootdoori.match.user.domain.value.Email;
import com.shootdoori.match.exception.common.DuplicatedException;
import com.shootdoori.match.exception.common.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {
    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateEmailNotDuplicated(String email) {
        if (userRepository.existsByEmail(Email.of(email))) {
            throw new DuplicatedException(ErrorCode.DUPLICATED_USER);
        }
    }
}
