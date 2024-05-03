package com.webApplication.videoShare.repository;

import com.webApplication.videoShare.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT comment FROM Comment comment" +
            " INNER JOIN comment.user user" +
            " INNER JOIN comment.video video " +
            "WHERE user.id = :userId AND video.id = :videoId")
    List<Comment> getComment(@Param("videoId") Long videoId, @Param("userId") Long userId);
}
