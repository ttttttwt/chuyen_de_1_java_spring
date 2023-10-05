package com.example.chuyen_de_1.controller;

import com.example.chuyen_de_1.model.Post;
import com.example.chuyen_de_1.model.ResponseObject;
import com.example.chuyen_de_1.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    @Autowired
    private PostRepository postRepository;
    // create CRUD for Post
    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllPost() {
        return ResponseEntity.ok(new ResponseObject("Ok", "success get all post", postRepository.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getPostById(@PathVariable Long id) {
        Optional<Post> checkPost = postRepository.findById(id);
        if (checkPost.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Error", "error to get post by id", ""));
        }
        return ResponseEntity.ok(new ResponseObject("Ok", "success get post by id", postRepository.findById(id)));
    }

    //get by user id
    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseObject> getPostByUserId(@PathVariable Long userId) {
        ArrayList<Post> checkPost = postRepository.findByuserId(userId);
        if (checkPost == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Error", "error to get post by user id", ""));
        }
        return ResponseEntity.ok(new ResponseObject("Ok", "success get post by user id", checkPost));
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createPost(@RequestBody Post post) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("created" , "success create post", postRepository.save(post)));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updatePost(@PathVariable Long id, @RequestBody Post post) {
        Post checkPost = postRepository.findById(id).orElse(null);
        if (checkPost != null) {
            checkPost.setTitle(post.getTitle());
            checkPost.setContent(post.getContent());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", "Update post success", postRepository.save(checkPost)));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Error", "Can't find id of post", ""));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deletePost(@PathVariable Long id) {
        Post checkPost = postRepository.findById(id).orElse(null);
        if (checkPost != null) {
            postRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", "Delete post success", ""));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Error", "Can't find id of post", ""));
    }


}
