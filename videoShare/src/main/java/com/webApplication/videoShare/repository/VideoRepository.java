package com.webApplication.videoShare.repository;

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

    @Query(value = "SELECT video FROM Video video WHERE video.videoId = :videoId")
    Video getVideoById(@Param("videoId") String videoId);

    @Query(value = "SELECT video FROM Video video INNER JOIN video.user user WHERE video.url = :url AND user.id != :userId")
    Video duplicateVideoCheck(@Param("url") String url, @Param("userId") Long userId);

}

