package com.intuit.CommentsService.controller;

import com.intuit.CommentsService.CommentRequest;
import com.intuit.CommentsService.entities.Comment;
import com.intuit.CommentsService.entities.Post;
import com.intuit.CommentsService.entities.User;
import com.intuit.CommentsService.repository.CommentRepository;
import com.intuit.CommentsService.repository.PostRepository;
import com.intuit.CommentsService.repository.UserRepository;
import com.intuit.CommentsService.response.CommentResponse;
import com.intuit.CommentsService.response.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Optional;

@RestController
@RequestMapping("/comments-service")
public class CommentsController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @PostMapping("/user")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PostMapping("/{userId}/posts")
    public PostResponse createPost(@PathVariable Long userId, @RequestBody Post post) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        post.setUser(user);
        post.setContent(post.getContent());
        post.setPostCreatedTime(new Timestamp(System.currentTimeMillis()));

        postRepository.save(post);

        return new PostResponse(post);
    }

    @PostMapping("/add/{postId}")
    public ResponseEntity<PostResponse> addCommentToPost(
            @PathVariable Long postId,
            @RequestBody CommentRequest commentRequest) {

        Optional<Post> postOptional = postRepository.findById(postId);
        Optional<User> userOptional = userRepository.findById(commentRequest.getId());

        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            User user;

            if (userOptional.isPresent()) {
                user = userOptional.get();
            } else {
                // Create a new user if userOptional is not present
                user = new User();
//                user.setUsername(commentRequest.getUserName());
                userRepository.save(user);
            }

            Comment comment = new Comment();
            comment.setContent(commentRequest.getContent());
            comment.setCommentCreatedTime(new Timestamp(System.currentTimeMillis()));
            comment.setUser(user);
            comment.setPost(post);

            post.getComments().add(comment);
            user.getComments().add(comment);

            postRepository.save(post);
//            userRepository.save(user);
            commentRepository.save(comment);

            // Create and return the PostResponse object
            PostResponse postResponse = new PostResponse(post);
            return ResponseEntity.ok(postResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

