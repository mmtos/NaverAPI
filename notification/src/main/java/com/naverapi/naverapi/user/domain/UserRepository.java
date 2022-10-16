package com.naverapi.naverapi.user.domain;

import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u " +
           "FROM GL_USER u " +
           "ORDER BY u.id DESC")
    Stream<User> findAllDesc();
}
