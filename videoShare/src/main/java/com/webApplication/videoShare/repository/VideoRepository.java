package com.webApplication.videoShare.repository;

import com.webApplication.videoShare.entity.Comment;
import com.webApplication.videoShare.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    @Query(value = "SELECT video FROM Video video INNER JOIN video.user user WHERE user.id = :userId")
    List<Video> fetchUserVideo(@Param("userId") Long userId);

    @Query(value = "SELECT video FROM Video video WHERE videoId = :videoId AND id = :id")
    Video getVideoByVideoId(@Param("videoId") String videoId, @Param("id") Long id);
}

