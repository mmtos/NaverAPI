package com.naverapi.naverapi.keyword.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeyWordRepository extends JpaRepository<KeyWord, Long> {
    List<KeyWord> findByUserId(Long userId);
    void deleteByUserId(Long userId);
}
