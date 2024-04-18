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
}
