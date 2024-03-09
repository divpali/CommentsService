package com.intuit.CommentsService.service;

import com.intuit.CommentsService.dto.CommentDto;
import com.intuit.CommentsService.entities.Comment;

public interface CommentService {

    public Comment createComment(CommentDto commentDto);
}
