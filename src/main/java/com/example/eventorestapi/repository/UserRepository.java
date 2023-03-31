package com.example.eventorestapi.repository;

import com.example.eventorestapi.models.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByEmail(String email);

    Optional<MyUser> findByUsername(String username);

    @Transactional
    void deleteByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
