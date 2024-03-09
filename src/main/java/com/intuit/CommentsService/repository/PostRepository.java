package com.intuit.CommentsService.repository;

import com.intuit.CommentsService.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // Find posts by user's ID
    List<Post> findByUserUserId(long userId);
}
