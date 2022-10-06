package com.naverapi.naverapi.keyword.domain;

import com.naverapi.naverapi.common.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "keyword")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KeyWord extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 256, nullable = false)
    private String keyword;

    @Column(length = 256, nullable = false)
    private Long userId;

    public KeyWord(String keyword, Long userId) {
        this.keyword = keyword;
        this.userId = userId;
    }
}
