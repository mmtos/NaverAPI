package com.naverapi.naverapi.user.domain;

import com.naverapi.naverapi.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity(name = "authuser")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 256, nullable = false)
    private String email;

    @Column(length = 256, nullable = false)
    private String name;

    @Column(length = 256, nullable = false)
    private String pictureLink;

    @Column(length = 256, nullable = false)
    private String role;

    @Builder
    public User(String email, String name, String pictureLink, String role) {
        this.email = email;
        this.name = name;
        this.pictureLink = pictureLink;
        this.role = role;
    }
}
