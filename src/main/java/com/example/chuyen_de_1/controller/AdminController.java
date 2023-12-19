package com.example.chuyen_de_1.controller;

import com.example.chuyen_de_1.model.ResponseObject;
import com.example.chuyen_de_1.repository.CommentRepository;
import com.example.chuyen_de_1.repository.PostRepository;
import com.example.chuyen_de_1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/statistic")
    public ResponseEntity<ResponseObject> getStatistic() {
        int userCount = (int) userRepository.count();
        int postCount = (int) postRepository.count();
        int commentCount = (int) commentRepository.count();

        Map<String, Integer> statisticMap = new HashMap<>();
        statisticMap.put("userCount", userCount);
        statisticMap.put("postCount", postCount);
        statisticMap.put("commentCount", commentCount);

        return ResponseEntity.ok(new ResponseObject("Ok", "success get statistic", statisticMap));
    }

}
