package com.intuit.CommentsService.controller;

import com.intuit.CommentsService.CommentRequest;
import com.intuit.CommentsService.CommentResponse;
import com.intuit.CommentsService.PostRequest;
import com.intuit.CommentsService.dto.CommentDTO;
import com.intuit.CommentsService.dto.PostDTO;
import com.intuit.CommentsService.dto.UserDTO;
import com.intuit.CommentsService.entities.Comment;
import com.intuit.CommentsService.entities.Post;
import com.intuit.CommentsService.entities.User;
import com.intuit.CommentsService.service.CommentService;
import com.intuit.CommentsService.service.PostService;
import com.intuit.CommentsService.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/comments-service")
public class CommentsServiceController {

    /*
    1. get and post Post
    2. get and post direct Comments of Post
    3. get list of replies for any Comment
    4. get likeDislike associated with Comment
    4. get likeDislike associated with Post
     */

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    private ModelMapper modelMapper;

    public CommentsServiceController() {
        this.modelMapper = new ModelMapper();
    }

    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestParam String userName) {
        UserDTO user = userService.createUser(userName);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> getUserById(@PathVariable Long user_id) {
        User user = userService.getUserById(user_id);
        return ResponseEntity.ok(modelMapper.map(user, UserDTO.class));
    }

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody PostRequest postRequest) {
        Post post = postService.createPost(postRequest);
        return ResponseEntity.ok(modelMapper.map(post, PostDTO.class));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable Long postId) {
        PostDTO post= postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }

    @PostMapping("/comment/{postId}")
    public ResponseEntity<?> addCommentToPost(@PathVariable Long postId, @RequestBody CommentRequest commentRequest) {

        User user = userService.getUserById(commentRequest.getUserId());
        if(user == null) {
            user = userService.createUser1(commentRequest.getUserName());
        }

        Comment comment = commentService.createCommentForPost(user, commentRequest);


        Post post = postService.addCommentToPost(postId, comment);

        return ResponseEntity.ok(modelMapper.map(post, PostDTO.class));
    }

    @GetMapping("/comment/{comment_id}")
    public ResponseEntity<?> getCommentById(@PathVariable Long comment_id) {
        Comment comment = commentService.getCommentById(comment_id);

        return ResponseEntity.ok(modelMapper.map(comment, CommentDTO.class));
    }
}












