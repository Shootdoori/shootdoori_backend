package com.shootdoori.match.entity.user;

import com.shootdoori.match.entity.common.Position;
import com.shootdoori.match.entity.common.SkillLevel;
import com.shootdoori.match.entity.common.TimeStamp;
import com.shootdoori.match.entity.user.value.*;
import com.shootdoori.match.team.domain.value.UniversityName;
import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private UserName name;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @Enumerated(EnumType.STRING)
    private Position position;

    @Enumerated(EnumType.STRING)
    private SkillLevel skillLevel;

    @Embedded
    private KakaoTalkId kakaoTalkId;

    @Embedded
    private UniversityName university;

    @Embedded
    private Department department;

    @Embedded
    private StudentYear studentYear;

    @Embedded
    private Bio bio;

    @Embedded
    private TimeStamp timeStamp = new TimeStamp();

    protected User() {}

    public User(
        UserName name,
        Email email,
        Password password,
        Position position,
        SkillLevel skillLevel,
        KakaoTalkId kakaoTalkId,
        UniversityName university,
        Department department,
        StudentYear studentYear,
        Bio bio
    ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.position = position;
        this.skillLevel = skillLevel;
        this.kakaoTalkId = kakaoTalkId;
        this.university = university;
        this.department = department;
        this.studentYear = studentYear;
        this.bio = bio;
    }

    public void update(Position position, SkillLevel skillLevel, Bio bio) {
        this.position = position;
        this.skillLevel = skillLevel;
        this.bio = bio;
    }

    public Long getId() {
        return id;
    }

    public UserName getName() {
        return this.name;
    }

    public Email getEmail() {
        return email;
    }

    public Position getPosition() {
        return position;
    }

    public SkillLevel getSkillLevel() {
        return skillLevel;
    }

    public KakaoTalkId getKakaoTalkId() {
        return kakaoTalkId;
    }

    public UniversityName getUniversity() {
        return university;
    }

    public Department getDepartment() {
        return department;
    }

    public StudentYear getStudentYear() {
        return studentYear;
    }

    public Bio getBio() {
        return bio;
    }

    public TimeStamp getTimeStamp() {
        return timeStamp;
    }
}
