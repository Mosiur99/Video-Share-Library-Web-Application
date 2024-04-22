package com.webApplication.videoShare.service;

import com.webApplication.videoShare.Entity.User;
import com.webApplication.videoShare.Entity.Video;
import com.webApplication.videoShare.repository.UserRepository;
import com.webApplication.videoShare.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
//        List<Video> videoList = videoRepository.findAll();
//        while(videoList.isEmpty()){
//
//        }
//        return videoRepository.findAll();
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

    @Override
    public void incrementLikeCount(String id) {
        Long userId = userService.fetchUserId();
        List<Video> videoList = videoRepository.findAll();
        List<User> userList = userRepository.findAll();
        User loginUser = null;
        for(User user : userList){
            if(user.getId().equals(userId)){
                loginUser = user;
                break;
            }
        }
        for(Video video : videoList){
            if(video.getVideoId().equals(id)){
                if(video.getUser() != null){
                    if(video.getUser().getId().equals(userId)){
                        return;
                    }
                    else{
                        List<User> likedUserList = videoList.getFirst().getLikedUser();
                        if(likedUserList.contains(loginUser)){
                            likedUserList.remove(loginUser);
                            video.setLikeCount(video.getLikeCount() - 1);
                            videoRepository.save(video);
                        }
                        else{
                            likedUserList.add(loginUser);
                            video.setLikeCount(video.getLikeCount() + 1);
                            videoRepository.save(video);
                        }
                    }
                }
//               long likes = video.getLikeCount();
//                likes += 1;
//                video.setLikeCount(likes);
//                videoRepository.save(video);
                break;
            }
        }
    }

    @Override
    public void incrementDislikeCount(String id) {
//        List<Video> videoList = videoRepository.findAll();
//        for(Video video : videoList){
//            if(video.getVideoId().equals(id)){
//                long dislikes = video.getDislikeCount();
//                dislikes += 1;
//                video.setDislikeCount(dislikes);
//                videoRepository.save(video);
//                break;
//            }
//        }

        Long userId = userService.fetchUserId();
        List<Video> videoList = videoRepository.findAll();
        List<User> userList = userRepository.findAll();
        User loginUser = null;
        for(User user : userList){
            if(user.getId().equals(userId)){
                loginUser = user;
                break;
            }
        }

        for(Video video : videoList){
            if(video.getVideoId().equals(id)) {
                if (video.getUser() != null) {
                    if (video.getUser().getId().equals(userId)) {
                        return;
                    } else {
                        List<User> dislikedUserList = videoList.getFirst().getDislikedUser();
                        if (dislikedUserList.contains(loginUser)) {
                            dislikedUserList.remove(loginUser);
                            video.setDislikeCount(video.getDislikeCount() - 1);
                            videoRepository.save(video);
                        } else {
                            dislikedUserList.add(loginUser);
                            video.setDislikeCount(video.getDislikeCount() + 1);
                            videoRepository.save(video);
                        }
                    }
                }
                break;
            }
        }
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
}
