package com.webApplication.videoShare.service;

import com.webApplication.videoShare.Entity.Video;

import java.util.List;

public interface VideoService {
    public List<Video> getAllVideos();

    public String extractVideoId(String url);

    public Video singleVideoDetails(String id);

    public void incrementLikeCount(String id);

    public void incrementDislikeCount(String id);
}
