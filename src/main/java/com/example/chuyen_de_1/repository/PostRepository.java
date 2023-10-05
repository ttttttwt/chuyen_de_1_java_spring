package com.example.chuyen_de_1.repository;

import com.example.chuyen_de_1.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface PostRepository extends JpaRepository<Post, Long> {
    String findBytitle(String title);
    // Find posts by userid
    ArrayList<Post> findByuserId(Long userId);
}
