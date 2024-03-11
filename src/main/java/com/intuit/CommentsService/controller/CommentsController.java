package com.intuit.CommentsService.controller;

import com.intuit.CommentsService.CommentRequest;
import com.intuit.CommentsService.entities.Comment;
import com.intuit.CommentsService.entities.Post;
import com.intuit.CommentsService.entities.User;
import com.intuit.CommentsService.repository.CommentRepository;
import com.intuit.CommentsService.repository.PostRepository;
import com.intuit.CommentsService.repository.UserRepository;
import com.intuit.CommentsService.response.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public Post createPost(@PathVariable Long userId, @RequestBody Post post) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        post.setUser(user);
        post.setContent(post.getContent());
        post.setPostCreatedTime(new Timestamp(System.currentTimeMillis()));

        return postRepository.save(post);
    }

    @PostMapping("/add/{postId}/{userId}")
    public ResponseEntity<PostResponse> addCommentToPost(
            @PathVariable Long postId,
            @PathVariable Long userId,
            @RequestBody CommentRequest commentRequest) {

        Optional<Post> postOptional = postRepository.findById(postId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (postOptional.isPresent() && userOptional.isPresent()) {
            Post post = postOptional.get();
            User user = userOptional.get();

            Comment comment = new Comment();
            comment.setContent(commentRequest.getContent());
            comment.setCommentCreatedTime(new Timestamp(System.currentTimeMillis()));
            comment.setUser(user);
            comment.setPost(post);

            post.getComments().add(comment);
//            user.getComments().add(comment);

            postRepository.save(post);
//            userRepository.save(user);
            commentRepository.save(comment);

            PostResponse postResponse = new PostResponse(post);
            return ResponseEntity.ok(postResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
