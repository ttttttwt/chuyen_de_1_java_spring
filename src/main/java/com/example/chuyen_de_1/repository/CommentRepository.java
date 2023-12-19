package com.example.chuyen_de_1.repository;

import com.example.chuyen_de_1.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    public ArrayList<Comment> findByPostId(Long postId);
}
