package com.webApplication.videoShare.service;

import com.webApplication.videoShare.entity.LikeOrDislike;
import com.webApplication.videoShare.entity.User;
import com.webApplication.videoShare.entity.Video;
import com.webApplication.videoShare.repository.UserRepository;
import com.webApplication.videoShare.repository.VideoRepository;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VideoServiceImpl implements VideoService{


    private final UserService userService;

    private final VideoRepository videoRepository;

    private final UserRepository userRepository;

    @Autowired
    public VideoServiceImpl(UserService userService, VideoRepository videoRepository, UserRepository userRepository) {
        this.userService = userService;
        this.videoRepository = videoRepository;
        this.userRepository = userRepository;
    }

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
    public Video singleVideoDetails(Long id) {

        Video video = videoRepository.getReferenceById(id);
        return video;
    }

    @Override
    public Long[] updateLikeOrDisLikeCount(Long id, String likeOrDisLike) {

        Long userId = userService.fetchUserId();
        Video video = videoRepository.getReferenceById(id);

        User loginUser = userRepository.findById(userId).orElse(null);

        if (video.getUser() != null && video.getUser().getId().equals(userId)) {
            Long[] ar = new Long[2];
            ar[0] = video.getLikeCount();
            ar[1] = video.getDislikeCount();
            return ar;
        }

        List<User> likedUserList = video.getLikedUser();
        List<User> dislikedUserList = video.getDislikedUser();

        if(likeOrDisLike.equals("LIKE")){
            if (likedUserList.contains(loginUser)) {
                likedUserList.remove(loginUser);
                video.setLikeCount(video.getLikeCount() - 1);
            } else if (dislikedUserList.contains(loginUser)) {
                dislikedUserList.remove(loginUser);
                likedUserList.add(loginUser);
                video.setDislikeCount(video.getDislikeCount() - 1);
                video.setLikeCount(video.getLikeCount() + 1);
                video.setLikeOrDislike(LikeOrDislike.LIKE);
            } else {
                likedUserList.add(loginUser);
                video.setLikeCount(video.getLikeCount() + 1);
                video.setLikeOrDislike(LikeOrDislike.LIKE);
            }
        }
        else{
            if (likedUserList.contains(loginUser)) {
                likedUserList.remove(loginUser);
                video.setDislikeCount(video.getDislikeCount() + 1);
                dislikedUserList.add(loginUser);
                video.setLikeCount(video.getLikeCount() - 1);
                video.setLikeOrDislike(LikeOrDislike.DISLIKE);
            } else if (dislikedUserList.contains(loginUser)) {
                dislikedUserList.remove(loginUser);
                video.setDislikeCount(video.getDislikeCount() - 1);
            } else {
                dislikedUserList.add(loginUser);
                video.setDislikeCount(video.getDislikeCount() + 1);
                video.setLikeOrDislike(LikeOrDislike.DISLIKE);
            }
        }

        Long[] ar = new Long[2];
        ar[0] = video.getLikeCount();
        ar[1] = video.getDislikeCount();
        videoRepository.save(video);

        return ar;
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
    public void viewCountUpdate(Long id) {

        Video video = videoRepository.getReferenceById(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            video.setViewCount(video.getViewCount() + 1);
            videoRepository.save(video);
            return;
        }

        long userId = userService.fetchUserId();
        if(video.getUser() != null){
            if(!video.getUser().getId().equals(id)){
                video.setViewCount(video.getViewCount() + 1);
                videoRepository.save(video);
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

    @Override
    public List<User> likedUsers(String videoId) {

        List<Video> videoList = videoRepository.findAll();
        Video video = videoList.stream()
                .filter(t -> videoId.equals(t.getVideoId()))
                .findFirst()
                .orElse(null);
        List<User> likedUserList = video.getLikedUser();
        return likedUserList;
    }

    @Override
    public List<User> dislikedUsers(String videoId) {

        List<Video> videoList = videoRepository.findAll();
        Video video = videoList.stream()
                .filter(t -> videoId.equals(t.getVideoId()))
                .findFirst()
                .orElse(null);
        List<User> dislikedUserList = video.getDislikedUser();
        return dislikedUserList;
    }
}
