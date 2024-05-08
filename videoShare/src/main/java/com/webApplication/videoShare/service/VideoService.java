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

    ResponseDTO action(Long id, String videoId, LikeOrDislike likeOrDislike);

    void addNewVideo(Long id, String url, String title, String previousVideoId);

    void viewCountUpdate(Long id, String videoId);

    ResponseDTO getDetails(Long id, String videoId);
}
