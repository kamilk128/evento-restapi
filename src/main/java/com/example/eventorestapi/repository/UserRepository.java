package com.example.eventorestapi.repository;

import com.example.eventorestapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    @Transactional
    void deleteByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
