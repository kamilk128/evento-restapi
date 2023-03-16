package com.example.eventorestapi.repository;

import com.example.eventorestapi.models.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Long> {
    MyUser findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);

    boolean existsByNick(String nick);

    boolean existsByEmail(String email);
}
