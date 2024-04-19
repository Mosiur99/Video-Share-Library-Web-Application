package com.webApplication.videoShare.service;

import com.webApplication.videoShare.Entity.Video;
import com.webApplication.videoShare.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class VideoServiceImpl implements VideoService{

    @Autowired
    private VideoRepository videoRepository;
    @Override
    public List<Video> getAllVideos() {
//        List<Video> videoList = videoRepository.findAll();
//        while(videoList.isEmpty()){
//
//        }
        return videoRepository.findAll();
    }

    @Override
    public String extractVideoId(String url) {
        return url.split("v=")[1];
    }

    @Override
    public Video singleVideoDetails(String id) {
        List<Video> videos = videoRepository.findAll();
        Video video = videos.stream()
                .filter(t -> id.equals(t.getVideoId()))
                .findFirst()
                .orElse(null);
        return video;
    }

    @Override
    public void incrementLikeCount(String id) {
        List<Video> videoList = videoRepository.findAll();
        for(Video video : videoList){
            if(video.getVideoId().equals(id)){
               long likes = video.getLikeCount();
                likes += 1;
                video.setLikeCount(likes);
                videoRepository.save(video);
                break;
            }
        }
    }

    @Override
    public void incrementDislikeCount(String id) {
        List<Video> videoList = videoRepository.findAll();
        for(Video video : videoList){
            if(video.getVideoId().equals(id)){
                long dislikes = video.getDislikeCount();
                dislikes += 1;
                video.setDislikeCount(dislikes);
                videoRepository.save(video);
                break;
            }
        }
    }
}
