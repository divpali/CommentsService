package com.intuit.CommentsService.service;

import com.intuit.CommentsService.CommentRequest;
import com.intuit.CommentsService.dto.CommentDTO;
import com.intuit.CommentsService.entities.Comment;
import com.intuit.CommentsService.entities.User;
import com.intuit.CommentsService.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;

public interface CommentService {

    Comment save(Comment comment);

    Comment getCommentById(Long commentId);

    Comment createCommentForPost(User user, CommentRequest commentRequest);

}
