package com.webApplication.videoShare.repository;

import com.webApplication.videoShare.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

//    @Query("SELECT url FROM videos")
//    List<Object> getVideosUrl();
}

