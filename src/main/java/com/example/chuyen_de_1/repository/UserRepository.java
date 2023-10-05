package com.example.chuyen_de_1.repository;

import com.example.chuyen_de_1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);

}
