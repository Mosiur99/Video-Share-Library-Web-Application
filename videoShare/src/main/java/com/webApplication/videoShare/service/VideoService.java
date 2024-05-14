package com.webApplication.videoShare.service;

import com.webApplication.videoShare.dto.ResponseDTO;
import com.webApplication.videoShare.entity.Activity;
import com.webApplication.videoShare.entity.Video;

import java.util.List;

public interface VideoService {
    List<Video> getAllVideos();

    List<Video> getAllVideosByUserId();

    String extractVideoId(String url);

    Video singleVideoDetails(String videoId, Long id);

    ResponseDTO updateLikeOrDislike(Long id, String videoId, Activity activity);

    void addNewVideo(Long id, String url, String title, String previousVideoId);

    void viewCountUpdate(Long id, String videoId);

    ResponseDTO getDetails(Long id, String videoId);
}
