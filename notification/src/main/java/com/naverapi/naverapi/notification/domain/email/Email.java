package com.naverapi.naverapi.notification.domain.email;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity(name = "email")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 256, nullable = false)
    private String address;

    @Column(length = 256, nullable = false)
    private String title;

    @Column(length = 4096, nullable = false)
    private String message;

    @Column(length = 4096, nullable = false)
    private String result;

    @Builder
    public Email(String address, String title, String message, String result){
        this.address = address;
        this.title = title;
        this.message = message;
        this.result = result;
    }
}
