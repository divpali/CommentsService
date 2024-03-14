package com.intuit.CommentsService.controller;

import com.intuit.CommentsService.CommentRequest;
import com.intuit.CommentsService.LikeDislikeType;
import com.intuit.CommentsService.entities.Comment;
import com.intuit.CommentsService.entities.LikeDislike;
import com.intuit.CommentsService.entities.Post;
import com.intuit.CommentsService.entities.User;
import com.intuit.CommentsService.repository.CommentRepository;
import com.intuit.CommentsService.repository.LikeDislikeRepository;
import com.intuit.CommentsService.repository.PostRepository;
import com.intuit.CommentsService.repository.UserRepository;
import com.intuit.CommentsService.response.CommentResponse;
import com.intuit.CommentsService.response.PostResponse;
import com.intuit.CommentsService.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
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

    @Autowired
    LikeDislikeRepository likeDislikeRepository;

    @PostMapping("/user")
    public UserResponse createUser(@RequestBody User user) {
        User user1 = userRepository.save(user);
        return new UserResponse(user1);
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
        Optional<User> userOptional = userRepository.findById(commentRequest.getUserId());

        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            User user;

            if (userOptional.isPresent()) {
                user = userOptional.get();
            } else {
                // Create a new user
                user = new User();
                user.setUsername(commentRequest.getUserName());
                userRepository.save(user);
            }

            // Check if the comment already exists
            Comment existingComment = commentRepository.findByContentAndUserAndPost(commentRequest.getCommentContent(), user, post);

            Comment comment;
            if (existingComment != null) {
                comment = existingComment;
            } else {
                comment = new Comment();
                comment.setContent(commentRequest.getCommentContent());
                comment.setCommentCreatedTime(new Timestamp(System.currentTimeMillis()));
                comment.setUser(user);
                comment.setPost(post);

                post.getComments().add(comment);
                user.getComments().add(comment);

                commentRepository.save(comment);
            }

            // Save the post
            postRepository.save(post);

            PostResponse postResponse = new PostResponse(post);
            return ResponseEntity.ok(postResponse);

            /*Comment comment = new Comment();
            comment.setContent(commentRequest.getContent());
            comment.setCommentCreatedTime(new Timestamp(System.currentTimeMillis()));
            comment.setUser(user);
            comment.setPost(post);

            post.getComments().add(comment);
            user.getComments().add(comment);

            postRepository.save(post);
//            userRepository.save(user);
            commentRepository.save(comment);

            PostResponse postResponse = new PostResponse(post);
            return ResponseEntity.ok(postResponse);*/
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /*@PostMapping("/add-reply/{postId}")
    public ResponseEntity<?> addReplyToComment(
            @PathVariable Long postId,
            @RequestBody CommentRequest commentRequest) {

        Optional<Post> postOptional = postRepository.findById(postId);

        if (postOptional.isPresent()) {
            Post post = postOptional.get();

            User user;

            if (commentRequest.getUserId() != null) {
                Optional<User> userOptional = userRepository.findById(commentRequest.getUserId());

                if (userOptional.isPresent()) {
                    user = userOptional.get();
                } else {
                    // Create a new user
                    user = new User();
                    user.setUsername(commentRequest.getUserName());
                    userRepository.save(user);
                }
            } else {
                return ResponseEntity.badRequest().body("UserId is required for comments.");
            }

            Comment parentComment;

            if (commentRequest.getParentCommentId() != null) {
                Optional<Comment> parentCommentOptional = commentRepository.findById(commentRequest.getParentCommentId());
                parentComment = parentCommentOptional.orElse(null);
            } else {
                parentComment = null; // No parent comment, direct reply to the post
            }

            Comment reply = new Comment();
            reply.setContent(commentRequest.getContent());
            reply.setCommentCreatedTime(new Timestamp(System.currentTimeMillis()));
            reply.setUser(user);
            reply.setPost(post);
            reply.setParentComment(parentComment);
            commentRepository.save(reply);

            if (parentComment != null) {
                parentComment.getReplies().add(reply);
            } else {
                post.getComments().add(reply);
            }

            postRepository.save(post);
//            userRepository.save(user);

            // Create and return the updated PostResponse object
            PostResponse postResponse = new PostResponse(post);
            return ResponseEntity.ok(postResponse);
        } else {
            return ResponseEntity.notFound().build();
        }


    }*/

    @PostMapping("/add-reply/{postId}")
    public ResponseEntity<?> addReplyToComment(
            @PathVariable Long postId,
            @RequestBody CommentRequest commentRequest) {

        Optional<Post> postOptional = postRepository.findById(postId);

        if (postOptional.isPresent()) {
            Post post = postOptional.get();

            // find parent comment
            Optional<Comment> parentCommentOptional = commentRepository.findById(commentRequest.getParentCommentId());

            if (!parentCommentOptional.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Comment parentComment = parentCommentOptional.get();

            //check if the user exists
            User user = null;
            Optional<User> userOptional = userRepository.findById(commentRequest.getUserId());
            if (!userOptional.isPresent()) {
                user = new User();
                user.setId(commentRequest.getUserId());
                user.setUsername(commentRequest.getUserName());
                userRepository.save(user);
            }

            //create the reply
            Comment reply = new Comment();
            reply.setContent(commentRequest.getCommentContent());
            reply.setCommentCreatedTime(new Timestamp(System.currentTimeMillis()));
            reply.setUser(user);
//            reply.setPost(post);
            reply.setParentComment(parentComment);

            commentRepository.save(reply);

            //add reply to parent comment & save the updated parent comment
            parentComment.getReplies().add(reply);
            commentRepository.saveAndFlush(parentComment);

            // Create and return the updated PostResponse object
            PostResponse postResponse = new PostResponse(post);
            return ResponseEntity.ok(postResponse);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

        //here we dont persist user info on user liking or disliking a post
    //we job keep count of like or dislike of post or comment

    @PostMapping("/like-post/{postId}")
    public ResponseEntity<?> likeOrDislikePost(@PathVariable Long postId, @RequestParam String likeDislikeType) {

        Optional<Post> postOptional = postRepository.findById(postId);

        if (postOptional.isPresent()) {
            Post post = postOptional.get();

            // Find existing like/dislike or create a new one
            LikeDislike existingLikeDislike = likeDislikeRepository.findByPost(post);

            if (existingLikeDislike != null) {
                // Update existing like/dislike
                LikeDislike likeDislike = existingLikeDislike;
                updateLikeDislikeCounts(likeDislike, likeDislikeType);

                likeDislikeRepository.save(likeDislike);
            } else {
                // Create a new like/dislike
                LikeDislike newLikeDislike = new LikeDislike();
                newLikeDislike.setPost(post);
                updateLikeDislikeCounts(newLikeDislike, likeDislikeType);

                likeDislikeRepository.save(newLikeDislike);
            }

            // Update like/dislike count in the associated post
            updatePostLikeDislikeCounts(post, likeDislikeType);

            PostResponse postResponse = new PostResponse(post);
            return ResponseEntity.ok(postResponse);
//            return ResponseEntity.ok("Post liked or disliked successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private void updateLikeDislikeCounts(LikeDislike likeDislike, String likeDislikeType) {
        if (likeDislikeType.equalsIgnoreCase("LIKE")) {
            Long likeCount = likeDislike.getPost().getLikeCount();
            if(likeCount == null) {
                likeDislike.getPost().setLikeCount(Long.valueOf(1));
            } else {
                likeDislike.getPost().setLikeCount(likeCount++);
            }
        } else if (likeDislikeType.equalsIgnoreCase("DISLIKE")) {
            Long disLikeCount = likeDislike.getPost().getDislikeCount() != null ? likeDislike.getPost().getDislikeCount() : 1;
            if(disLikeCount == null) {
                likeDislike.getPost().setLikeCount(Long.valueOf(1));
            } else {
                likeDislike.getPost().setLikeCount(disLikeCount++);
            }
        }
    }

    private void updatePostLikeDislikeCounts(Post post, String likeDislikeType) {
        if (likeDislikeType.equalsIgnoreCase("LIKE")) {
            post.setLikeCount(post.getLikeCount() + 1);
        } else if (likeDislikeType.equalsIgnoreCase("DISLIKE")) {
            post.setDislikeCount(post.getDislikeCount() + 1);
        }
    }

    @PostMapping("/like-comment/{commentId}")
    public ResponseEntity<String> likeOrDislikeComment(
            @PathVariable Long commentId,
            @RequestParam String likeDislikeType) {

        // Find the comment
        Optional<Comment> commentOptional = commentRepository.findById(commentId);

        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();

            // Find existing like/dislike or create a new one
            LikeDislike existingLikeDislike = likeDislikeRepository.findByComment(comment);

            if (existingLikeDislike != null) {
                // Update existing like/dislike
                LikeDislike likeDislike = existingLikeDislike;
                updateLikeDislikeCounts(likeDislike, likeDislikeType);

                likeDislikeRepository.save(likeDislike);
            } else {
                // Create a new like/dislike
                LikeDislike newLikeDislike = new LikeDislike();
                newLikeDislike.setComment(comment);
                updateLikeDislikeCounts(newLikeDislike, likeDislikeType);

                likeDislikeRepository.save(newLikeDislike);
            }

            // Update like/dislike count in the associated comment
            updateCommentLikeDislikeCounts(comment, likeDislikeType);

            return ResponseEntity.ok("Comment liked or disliked successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /*private void updateLikeDislikeCounts(LikeDislike likeDislike, String likeDislikeType) {
        if (likeDislikeType.equalsIgnoreCase("LIKE")) {
            likeDislike.setType(LikeDislikeType.LIKE);
        } else if (likeDislikeType.equalsIgnoreCase("DISLIKE")) {
            likeDislike.setType(LikeDislikeType.DISLIKE);
        }
    }*/

    private void updateCommentLikeDislikeCounts(Comment comment, String likeDislikeType) {
        long likeCount = comment.getLikesDislikes().stream().filter(like -> like.getType() == LikeDislikeType.LIKE).count();
        long dislikeCount = comment.getLikesDislikes().stream().filter(like -> like.getType() == LikeDislikeType.DISLIKE).count();

        comment.setLikeCount(likeCount);
        comment.setDislikeCount(dislikeCount);
    }


}

