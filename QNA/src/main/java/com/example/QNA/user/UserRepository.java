package com.example.QNA.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByUserId(String userId);
    Boolean existsByEmail(String email);
    Optional<User> findByUserName(String username);

    Optional<User> findByUserId(String userId);
}
