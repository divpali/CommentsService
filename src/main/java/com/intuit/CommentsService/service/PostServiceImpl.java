package com.intuit.CommentsService.service;

import com.intuit.CommentsService.dto.PostDto;
import com.intuit.CommentsService.dto.UserDto;
import com.intuit.CommentsService.entities.Comment;
import com.intuit.CommentsService.entities.Post;
import com.intuit.CommentsService.entities.User;
import com.intuit.CommentsService.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserService userService;

    private ModelMapper modelMapper;

    public PostServiceImpl() {
        modelMapper = new ModelMapper();
    }

    @Override
    public PostDto createPost(long userId, String content) {

        User user = userService.getUserById(userId);

        Post post = new Post();
        post.setContent(content);
        post.setPostCreatedDate(new Timestamp(System.currentTimeMillis()));
        post.setUser(user);

        Post postSaved = postRepository.save(post);
        return modelMapper.map(postSaved, PostDto.class);
    }

    @Override
    public List<Post> getPostsByUserId(long userId) {
        return postRepository.findByUserUserId(userId);
    }

    @Override
    public PostDto getPostById(long postId) {
        Post post =  postRepository.findById(postId).get();
        PostDto postDto = modelMapper.map(post, PostDto.class);
        return postDto;
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    private static User buildUser(UserDto userDTO) {
        return User.builder().username(userDTO.getUserName()).userId(userDTO.getUserId()).build();
    }
}
