package com.webApplication.videoShare.service;

import com.webApplication.videoShare.entity.LikeOrDislike;
import com.webApplication.videoShare.entity.User;
import com.webApplication.videoShare.entity.Video;
import com.webApplication.videoShare.repository.UserRepository;
import com.webApplication.videoShare.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class VideoServiceImpl implements VideoService{


    private final UserService userService;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;

    @Autowired
    public VideoServiceImpl(UserService userService,
                            VideoRepository videoRepository,
                            UserRepository userRepository) {
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
        return videoRepository.fetchUserVideo(userId);
    }

    @Override
    public String extractVideoId(String url) {
        String string = url.split("v=")[1];
        return string.split("=")[0];
    }

    @Override
    public Video singleVideoDetails(Long id) {
        return videoRepository.getReferenceById(id);
    }

    @Override
    public List<Long> updateLikeOrDisLikeCount(Long id,
                                           LikeOrDislike likeOrDisLike) {
        Long userId = userService.fetchUserId();
        Video video = videoRepository.getReferenceById(id);
        List<User> likedUserList = video.getLikedUser();
        List<User> dislikedUserList = video.getDislikedUser();
        User loginUser = userRepository.findById(userId).orElse(null);

        if (Objects.nonNull(video.getUser()) && video.getUser().getId().equals(userId)) {
            return updateVideoInformation(video, likedUserList, dislikedUserList, likeOrDisLike);
        }

        if(likedUserList.contains(loginUser) &&
                likeOrDisLike.equals(LikeOrDislike.LIKE)){
            likedUserList.remove(loginUser);
            video.setLikeCount(video.getLikeCount() - 1);
            return updateVideoInformation(video, likedUserList, dislikedUserList, likeOrDisLike);
        }

        if(!likedUserList.contains(loginUser) && dislikedUserList.contains(loginUser) && likeOrDisLike.equals(LikeOrDislike.LIKE)){
            dislikedUserList.remove(loginUser);
            likedUserList.add(loginUser);
            video.setDislikeCount(video.getDislikeCount() - 1);
            video.setLikeCount(video.getLikeCount() + 1);
            return updateVideoInformation(video, likedUserList, dislikedUserList, likeOrDisLike);
        }

        if(!likedUserList.contains(loginUser) && !dislikedUserList.contains(loginUser) && likeOrDisLike.equals(LikeOrDislike.LIKE)){
            likedUserList.add(loginUser);
            video.setLikeCount(video.getLikeCount() + 1);
            return updateVideoInformation(video, likedUserList, dislikedUserList, likeOrDisLike);
        }

        if(dislikedUserList.contains(loginUser) && likeOrDisLike.equals(LikeOrDislike.DISLIKE)){
            dislikedUserList.remove(loginUser);
            video.setDislikeCount(video.getDislikeCount() - 1);
            return updateVideoInformation(video, likedUserList, dislikedUserList, likeOrDisLike);
        }

        if(!dislikedUserList.contains(loginUser) && likedUserList.contains(loginUser) && likeOrDisLike.equals(LikeOrDislike.DISLIKE)){
            likedUserList.remove(loginUser);
            video.setDislikeCount(video.getDislikeCount() + 1);
            dislikedUserList.add(loginUser);
            video.setLikeCount(video.getLikeCount() - 1);
            return updateVideoInformation(video, likedUserList, dislikedUserList, likeOrDisLike);
        }

        dislikedUserList.add(loginUser);
        video.setDislikeCount(video.getDislikeCount() + 1);
        return updateVideoInformation(video, likedUserList, dislikedUserList, likeOrDisLike);
    }

    @Override
    public void newVideoAdded(String title,
                              String url,
                              Long id) {
        Video video = new Video();
        video.setTitle(title);
        video.setUrl(url);
        String videoId = extractVideoId(url);
        video.setVideoId(videoId);
        video.setUser(userRepository.fetchUserById(id));
        videoRepository.save(video);
    }

    @Override
    public void viewCountUpdate(Long id) {
        Video video = videoRepository.getReferenceById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(Objects.isNull(authentication) || authentication instanceof AnonymousAuthenticationToken){
            video.setViewCount(video.getViewCount() + 1);
            videoRepository.save(video);
            return;
        }

        Long userId = userService.fetchUserId();
        if(Objects.nonNull(video.getUser()) && !video.getUser().getId().equals(userId)){
            video.setViewCount(video.getViewCount() + 1);
            videoRepository.save(video);
        }
    }

    @Override
    public void updateVideo(Long id,
                            String title,
                            String url) {
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
        Video video = videoRepository.fetchVideoByVideoId(videoId);
        return video.getLikedUser();
    }

    @Override
    public List<User> dislikedUsers(String videoId) {
        Video video = videoRepository.fetchVideoByVideoId(videoId);
        return video.getDislikedUser();
    }

    public List<Long> updateVideoInformation(Video video,
                                             List<User> likedUserList,
                                             List<User> dislikedUserList,
                                             LikeOrDislike likeOrDislike){
        video.setLikedUser(likedUserList);
        video.setDislikedUser(dislikedUserList);
        video.setLikeOrDislike(likeOrDislike);
        List<Long> actionCount = new ArrayList<>();
        actionCount.add(video.getLikeCount());
        actionCount.add(video.getDislikeCount());
        videoRepository.save(video);
        return actionCount;
    }
}
