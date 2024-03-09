package com.intuit.CommentsService.service;

import com.intuit.CommentsService.dto.PostDto;
import com.intuit.CommentsService.entities.Post;

import java.util.List;

public interface PostService {

    public PostDto createPost (long userId, String postContent);

    public List<Post> getPostsByUserId(long userId);

    // Assuming you have a method to retrieve the post by postId
    public PostDto getPostById(long postId);

    public List<Post> getAllPosts();

}
