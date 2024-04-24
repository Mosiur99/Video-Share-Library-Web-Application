package com.webApplication.videoShare.service;

import com.webApplication.videoShare.entity.Video;

import java.util.List;

public interface VideoService {
    List<Video> getAllVideos();

    List<Video> getAllVideosByUserId();

    String extractVideoId(String url);

    Video singleVideoDetails(String id);

    Long updateLikeCount(String id);

    Long updateDislikeCount(String id);

    void newVideoAdded(String title, String url, long id);

    void viewCountUpdate(String id);

    void updateVideo(Long id, String title, String url);
}
