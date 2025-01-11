package com.example.webappassignment4.repository;

import com.example.webappassignment4.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);

    User findByUserName(String userName);
    User findByEmail(String email);
}
