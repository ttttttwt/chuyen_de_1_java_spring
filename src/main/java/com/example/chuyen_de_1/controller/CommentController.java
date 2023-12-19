package com.example.chuyen_de_1.controller;

import com.example.chuyen_de_1.model.Comment;
import com.example.chuyen_de_1.model.ResponseObject;
import com.example.chuyen_de_1.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllComment() {
        return ResponseEntity.ok(new ResponseObject("Ok", "success get all comment", commentRepository.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getCommentById(@PathVariable Long id) {
        Comment checkComment = commentRepository.findById(id).orElse(null);
        if (checkComment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Error", "error to get comment by id", ""));
        }
        return ResponseEntity.ok(new ResponseObject("Ok", "success get comment by id", checkComment));
    }

    @GetMapping("post/{postId}")
    public ResponseEntity<ResponseObject> getCommentByPostId(@PathVariable Long postId) {
        ArrayList<Comment> checkComment = commentRepository.findByPostId(postId);
        if (checkComment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Error", "error to get comment by post id", ""));
        }
        return ResponseEntity.ok(new ResponseObject("Ok", "success get comment by post id", checkComment));
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createComment(@RequestBody Comment comment) {
        Comment checkComment = commentRepository.save(comment);
        if (checkComment == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("Error", "error to create comment", ""));
        }
        return ResponseEntity.ok(new ResponseObject("Ok", "success create comment", checkComment));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseObject> updateComment(@RequestBody Comment comment) {
        Comment checkComment = commentRepository.findById(comment.getId()).orElse(null);
        if (checkComment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Error", "error to update comment", ""));
        }
        checkComment.setContent(comment.getContent());
        return ResponseEntity.ok(new ResponseObject("Ok", "success update comment", commentRepository.save(checkComment)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteComment(@PathVariable Long id) {
        Comment checkComment = commentRepository.findById(id).orElse(null);
        if (checkComment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Error", "error to delete comment", ""));
        }
        commentRepository.deleteById(id);
        return ResponseEntity.ok(new ResponseObject("Ok", "success delete comment", ""));
    }
}

