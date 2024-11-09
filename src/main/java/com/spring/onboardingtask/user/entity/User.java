package com.spring.onboardingtask.user.entity;

import com.spring.onboardingtask.global.entity.Timestamped;
import com.spring.onboardingtask.user.eums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "db_user")
public class User extends Timestamped {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;
    @Column(name = "authority_name", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole authorityName = UserRole.USER;

    @Builder
    public User(String username, String password, String nickname, UserRole authorityName) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.authorityName = authorityName;
    }

}
