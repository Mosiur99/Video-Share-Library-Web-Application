package com.webApplication.videoShare.service;

import com.webApplication.videoShare.entity.LikeOrDislike;
import com.webApplication.videoShare.entity.User;
import com.webApplication.videoShare.entity.Video;

import java.util.List;

public interface VideoService {
    List<Video> getAllVideos();

    List<Video> getAllVideosByUserId();

    String extractVideoId(String url);

    Video singleVideoDetails(Long id);

    List<Long> updateLikeOrDisLikeCount(Long id, LikeOrDislike likeOrDislike);

    void newVideoAdded(String title, String url, Long id);

    void viewCountUpdate(Long id);

    void updateVideo(Long id, String title, String url);

    List<User> likedUsers(String videoId);

    List<User> dislikedUsers(String videoId);
}
