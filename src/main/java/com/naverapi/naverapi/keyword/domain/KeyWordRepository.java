package com.naverapi.naverapi.keyword.domain;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface KeyWordRepository extends JpaRepository<KeyWord, Long> {
    List<KeyWord> findByUserId(Long userId);
    void deleteByUserId(Long userId);
}
