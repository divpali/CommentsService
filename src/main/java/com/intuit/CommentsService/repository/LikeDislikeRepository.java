package com.intuit.CommentsService.repository;

import com.intuit.CommentsService.entities.Comment;
import com.intuit.CommentsService.entities.LikeDislike;
import com.intuit.CommentsService.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeDislikeRepository extends JpaRepository<LikeDislike, Long> {

    LikeDislike findByPost(Post post);

    LikeDislike findByComment(Comment comment);
}
