package com.intuit.CommentsService.service;

import com.intuit.CommentsService.CommentRequest;
import com.intuit.CommentsService.PostRequest;
import com.intuit.CommentsService.dto.PostDTO;
import com.intuit.CommentsService.entities.Comment;
import com.intuit.CommentsService.entities.Post;
import com.intuit.CommentsService.entities.User;

import java.util.List;

public interface PostService {

    Post createPost(PostRequest postRequest);

    PostDTO getPostById(Long postId);

    Post getPostById1(Long postId);

    Post savePostWithNewComments(Post post);

    Post addCommentToPost(Long postId, Comment comment);

}
