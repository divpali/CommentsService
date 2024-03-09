package com.intuit.CommentsService.controller;


import com.intuit.CommentsService.PostResponse;
import com.intuit.CommentsService.dto.PostDto;
import com.intuit.CommentsService.dto.UserDto;
import com.intuit.CommentsService.entities.Post;
import com.intuit.CommentsService.entities.User;
import com.intuit.CommentsService.service.PostService;
import com.intuit.CommentsService.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/comments-service1")
public class PhaseController {

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    ModelMapper modelMapper;

    PhaseController() {
        modelMapper = new ModelMapper();
    }

    @GetMapping("/all_user")
    public ResponseEntity<?> getAllUser() {

        List<User> users = userService.getAllUser();
        return ResponseEntity.ok().body(users);

    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long userId) {

        User user = userService.getUserById(userId);
        return ResponseEntity.ok().body(user);

    }

//    @GetMapping("/all_post")
//    public ResponseEntity<?> getAllPosts() {
//        List<Post> posts = postService.getAllPosts();
//        List<PostResponse> postResponseList = new ArrayList<>();
//        for (Post post : posts) {
//            PostResponse postResponse = PostResponse.fromEntity(post);
//            postResponseList.add(postResponse);
//        }
//        return ResponseEntity.ok(postResponseList);
//    }

    @GetMapping("/post/{post_id}")
    public ResponseEntity<?> getPostById(@PathVariable long post_id) {
        PostDto postDto = postService.getPostById(post_id);
        Post post = modelMapper.map(postDto, Post.class);
        return ResponseEntity.ok(post);
    }
}
