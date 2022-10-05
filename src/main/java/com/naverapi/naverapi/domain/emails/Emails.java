package com.naverapi.naverapi.domain.emails;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity(name = "email")
public class Emails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


}
