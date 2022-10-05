package com.naverapi.naverapi.domain.keywords;

import com.naverapi.naverapi.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity(name = "keyword")
public class KeyWord extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 256, nullable = false)
    private String name;

    @Builder
    public KeyWord(String name) {
        this.name = name;
    }
}
