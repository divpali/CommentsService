package com.intuit.CommentsService.service;

import com.intuit.CommentsService.CommentRequest;
import com.intuit.CommentsService.PostRequest;
import com.intuit.CommentsService.dto.CommentDTO;
import com.intuit.CommentsService.dto.PostDTO;
import com.intuit.CommentsService.dto.UserDTO;
import com.intuit.CommentsService.entities.Comment;
import com.intuit.CommentsService.entities.Post;
import com.intuit.CommentsService.entities.User;
import com.intuit.CommentsService.repository.CommentRepository;
import com.intuit.CommentsService.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserService userService;

    private ModelMapper modelMapper;

    public PostServiceImpl() {
        modelMapper = new ModelMapper();
    }


    @Override
    public Post createPost(PostRequest postRequest) {

        User user = userService.getUserById(postRequest.getUserId());

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        PostDTO postDTO = PostDTO.builder()
                .postContent(postRequest.getPostContent())
                .postCreatedDate(new Timestamp(System.currentTimeMillis()))
                .userDTO(userDTO)
                .build();
        
        Post post = modelMapper.map(postDTO, Post.class);
        post.setUser(user);

        return postRepository.save(post);
    }

    @Override
    public PostDTO getPostById(Long postId) {
        Post post = postRepository.findById(postId).get();
        return modelMapper.map(post, PostDTO.class);
    }

    @Override
    public Post getPostById1(Long postId) {
        return postRepository.findById(postId).get();
    }

    @Override
    public Post savePostWithNewComments(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post addCommentToPost(Long postId, Comment comment) {

        Post post = getPostById1(postId);
        post.addComments(comment);

        postRepository.save(post);
        return post;
    }


}
