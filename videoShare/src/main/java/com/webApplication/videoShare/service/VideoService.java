package com.webApplication.videoShare.service;

import com.webApplication.videoShare.dto.ResponseDTO;
import com.webApplication.videoShare.entity.LikeOrDislike;
import com.webApplication.videoShare.entity.Video;

import java.util.List;

public interface VideoService {
    List<Video> getAllVideos();

    List<Video> getAllVideosByUserId();

    String extractVideoId(String url);

    Video singleVideoDetails(String videoId, Long id);

    ResponseDTO updateLikeOrDisLikeCount(String videoId, Long id, LikeOrDislike likeOrDislike);

    void addNewVideo(String title, String url, Long id);

    void viewCountUpdate(String videoId, Long id);

    void updateVideo(String videoId, Long id, String title, String url);

    ResponseDTO getDetails(String videoId, Long id);
}
