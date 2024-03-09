package com.intuit.CommentsService.service;

import com.intuit.CommentsService.dto.CommentDto;
import com.intuit.CommentsService.entities.Comment;
import com.intuit.CommentsService.entities.Post;
import com.intuit.CommentsService.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl() {
        modelMapper = new ModelMapper();
    }

    @Override
    public Comment createComment(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);

        Post post = comment.getPost();
        if (post != null) {
            comment.setPost(post);
        } else {
            // Handle the case where the post is not set in the DTO
            // Maybe throw an exception or handle it based on your requirements
        }

        return commentRepository.save(comment);
    }

}
