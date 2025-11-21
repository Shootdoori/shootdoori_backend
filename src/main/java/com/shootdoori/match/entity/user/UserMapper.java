package com.shootdoori.match.entity.user;

import com.shootdoori.match.entity.user.dto.ProfileResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public ProfileResponse toProfileResponse(User user) {
        if (user == null) {
            return null;
        }

        return new ProfileResponse(
            user.getName().value(),
            user.getSkillLevel().name(),
            user.getEmail().value(),
            user.getKakaoTalkId().value(),
            user.getPosition().name(),
            user.getUniversity().universityName(),
            user.getDepartment().value(),
            user.getStudentYear().value(),
            user.getBio().value(),
            user.getTimeStamp().getCreatedAt()
        );
    }
}