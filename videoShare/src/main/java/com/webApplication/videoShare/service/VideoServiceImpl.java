package com.webApplication.videoShare.service;

import com.webApplication.videoShare.entity.User;
import com.webApplication.videoShare.entity.Video;
import com.webApplication.videoShare.repository.UserRepository;
import com.webApplication.videoShare.repository.VideoRepository;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class VideoServiceImpl implements VideoService{

    @Autowired
    private UserService userService;
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

    @Override
    public List<Video> getAllVideosByUserId() {
        Long userId = userService.fetchUserId();
        List<Video> videoList = videoRepository.findAll();
        List<Video> userVideoList = new ArrayList<>();
        for(Video video : videoList){
            if(video.getUser() != null){
                if(video.getUser().getId().equals(userId)){
                    userVideoList.add(video);
                }
            }
        }
        return userVideoList;
    }

    @Override
    public String extractVideoId(String url) {

        String string = url.split("v=")[1];
        return string.split("=")[0];
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
    

    public Long updateLikeCount(String id) {
        Long userId = userService.fetchUserId();
        List<Video> videoList = videoRepository.findAll();
        Video video = videoList.stream()
                .filter(t -> id.equals(t.getVideoId()))
                .findFirst()
                .orElse(null);
        if (Objects.isNull(video)) {
            return null;
        }

        User loginUser = userRepository.findById(userId).orElse(null);
        if (loginUser == null) {
            return null;
        }

        if (video.getUser() != null && video.getUser().getId().equals(userId)) {
            return video.getLikeCount();
        }

        List<User> likedUserList = video.getLikedUser();
        List<User> dislikedUserList = video.getDislikedUser();

        if (likedUserList.contains(loginUser)) {
            likedUserList.remove(loginUser);
            video.setLikeCount(video.getLikeCount() - 1);
        } else if (dislikedUserList.contains(loginUser)) {
            dislikedUserList.remove(loginUser);
            likedUserList.add(loginUser);
            video.setDislikeCount(video.getDislikeCount() - 1);
            video.setLikeCount(video.getLikeCount() + 1);
        } else {
            likedUserList.add(loginUser);
            video.setLikeCount(video.getLikeCount() + 1);
        }

        videoRepository.save(video);
        return video.getLikeCount();
    }

    public Long updateDislikeCount(String id) {
        Long userId = userService.fetchUserId();
        List<Video> videoList = videoRepository.findAll();
        Video video = null;
        for(Video v : videoList){
            if(v.getVideoId().equals(id)){
                video = v;
                break;
            }
        }
        if (Objects.isNull(video)) {
            return null;
        }

        User loginUser = userRepository.findById(userId).orElse(null);
        if (loginUser == null) {
            return null;
        }

        if (video.getUser() != null && video.getUser().getId().equals(userId)) {
            return video.getDislikeCount();
        }

        List<User> likedUserList = video.getLikedUser();
        List<User> dislikedUserList = video.getDislikedUser();

        if (likedUserList.contains(loginUser)) {
            likedUserList.remove(loginUser);
            video.setDislikeCount(video.getDislikeCount() + 1);
            dislikedUserList.add(loginUser);
            video.setLikeCount(video.getLikeCount() - 1);
        } else if (dislikedUserList.contains(loginUser)) {
            dislikedUserList.remove(loginUser);
            video.setDislikeCount(video.getDislikeCount() - 1);
        } else {
            dislikedUserList.add(loginUser);
            video.setDislikeCount(video.getDislikeCount() + 1);
        }

        videoRepository.save(video);
        return video.getDislikeCount();
    }


    @Override
    public void newVideoAdded(String title, String url, long id) {
        Video video = new Video();
        video.setTitle(title);
        video.setUrl(url);
        String videoId = extractVideoId(url);
        video.setVideoId(videoId);
        List<User> userList = userRepository.findAll();
        for(User user : userList){
            if(user.getId().equals(id)){
                video.setUser(user);
                break;
            }
        }
        videoRepository.save(video);
    }

    @Override
    public void viewCountUpdate(String id) {
        long userId = userService.fetchUserId();
        List<Video> videoList = videoRepository.findAll();

        for(Video video : videoList){
            if(video.getVideoId().equals(id)){
                if(video.getUser() != null){
                    if(video.getUser().getId().equals(userId)){
                        return;
                    }
                    else{
                        video.setViewCount(video.getViewCount() + 1);
                        videoRepository.save(video);
                    }
                }
            }
        }
    }

    @Override
    public void updateVideo(Long id, String title, String url) {
        Video video = videoRepository.getReferenceById(id);
        video.setTitle(title);
        video.setUrl(url);
        video.setVideoId(extractVideoId(url));
        video.setLikeCount(0);
        video.setDislikeCount(0);
        video.setViewCount(0);
        video.setLikedUser(null);
        video.setDislikedUser(null);
        videoRepository.save(video);
    }
}
