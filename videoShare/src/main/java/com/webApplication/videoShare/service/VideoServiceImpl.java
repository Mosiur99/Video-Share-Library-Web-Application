package com.webApplication.videoShare.service;

import com.webApplication.videoShare.Entity.Video;
import com.webApplication.videoShare.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService{

    @Autowired
    private VideoRepository videoRepository;
    @Override
    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }
}
