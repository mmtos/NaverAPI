package com.naverapi.naverapi.article.domain.email;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity(name = "email")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


}
