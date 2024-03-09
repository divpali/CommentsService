package com.intuit.CommentsService.controller;

import com.intuit.CommentsService.PostResponse;
import com.intuit.CommentsService.dto.CommentDto;
import com.intuit.CommentsService.dto.PostDto;
import com.intuit.CommentsService.dto.UserDto;
import com.intuit.CommentsService.entities.Comment;
import com.intuit.CommentsService.entities.Post;
import com.intuit.CommentsService.entities.User;
import com.intuit.CommentsService.service.CommentService;
import com.intuit.CommentsService.service.PostService;
import com.intuit.CommentsService.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
    PostService postService;

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;
    
    ModelMapper modelMapper;

    @ModelAttribute
    public void userInit() {    //getting user info from 0Auth Setup
        modelMapper = new ModelMapper();
    }

    @GetMapping("/ping")
    public ResponseEntity<?> healthStatus() {
        return ResponseEntity.ok().body("ping!");
    }

    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestParam String userName) {

        userService.save(userName);
        return ResponseEntity.ok("User created");

    }

    @PostMapping("/post")
    public ResponseEntity<PostResponse> createPostByUser(@RequestParam Long userId, @RequestBody String postContent) {
        try {
            PostDto createdPost = postService.createPost(userId, postContent);
            PostResponse responseDto = PostResponse.fromEntity(createdPost);
            return ResponseEntity.ok(responseDto);
//            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/post/{post_id}/comment")
    public ResponseEntity<PostResponse> addCommentToPost(@PathVariable("post_id") long post_id, @RequestBody String content, @RequestParam Long userId) {

        User user = userService.getUserById(userId);

        UserDto userDto = modelMapper.map(user, UserDto.class);
        PostDto postDto = postService.getPostById(post_id);

        //create CommentDto
        CommentDto commentDto = new CommentDto();
        commentDto.setText(content);
        commentDto.setComment_created_date(System.currentTimeMillis());
        commentDto.setUserDto(userDto);
        commentDto.setPostDto(postDto);

        if (postDto.getCommentDtoList() == null) {
            new ArrayList<>().add(commentDto);
        } else {
            postDto.getCommentDtoList().add(commentDto);
        }

        PostResponse postResponse = PostResponse.fromEntity(postDto);

        return ResponseEntity.ok(postResponse);

    }
}












