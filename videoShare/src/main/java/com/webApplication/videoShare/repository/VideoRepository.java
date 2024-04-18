package com.webApplication.videoShare.repository;

import com.webApplication.videoShare.Entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

//    @Query("SELECT url FROM videos")
//    List<Object> getVideosUrl();
}

