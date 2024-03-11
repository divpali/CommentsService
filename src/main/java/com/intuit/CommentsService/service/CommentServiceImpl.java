package com.intuit.CommentsService.service;

import com.intuit.CommentsService.CommentRequest;
import com.intuit.CommentsService.dto.CommentDTO;
import com.intuit.CommentsService.dto.UserDTO;
import com.intuit.CommentsService.entities.Comment;
import com.intuit.CommentsService.entities.Post;
import com.intuit.CommentsService.entities.User;
import com.intuit.CommentsService.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentRepository commentRepository;

    private ModelMapper modelMapper;

    public CommentServiceImpl() {
        modelMapper = new ModelMapper();
    }


    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).get();
    }

    @Override
    public Comment createCommentForPost(User user, CommentRequest commentRequest) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCommentContent(commentRequest.getCommentContent());
        commentDTO.setCommentCreatedTime(new Timestamp(System.currentTimeMillis()));
        commentDTO.setUserDTO(modelMapper.map(user, UserDTO.class));

        Comment comment = modelMapper.map(commentDTO, Comment.class);
        return commentRepository.save(comment);
    }
}
