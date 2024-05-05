package com.webApplication.videoShare.service;

import com.webApplication.videoShare.dto.ResponseDTO;
import com.webApplication.videoShare.entity.LikeOrDislike;
import com.webApplication.videoShare.entity.User;
import com.webApplication.videoShare.entity.Video;
import com.webApplication.videoShare.exception.ResourceNotFoundException;
import com.webApplication.videoShare.repository.UserRepository;
import com.webApplication.videoShare.repository.VideoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public Video singleVideoDetails(String videoId, Long id) {
        Video video = videoRepository.getVideoByVideoId(videoId, id);
        if(Objects.isNull(video)) {
            throw new ResourceNotFoundException();
        }

        return video;
    }

    @Override
    @Transactional
    public ResponseDTO updateLikeOrDisLikeCount(String videoId,
                                                Long id,
                                                LikeOrDislike likeOrDisLike) {
        Long userId = userService.fetchUserId();
        Video video = videoRepository.getVideoByVideoId(videoId, id);
        if(Objects.isNull(video)){
            throw new ResourceNotFoundException();
        }
        List<User> likedUserList = video.getLikedUser();
        List<User> dislikedUserList = video.getDislikedUser();
        Optional<User> loginUser = userRepository.findById(userId);

        if(loginUser.isEmpty()){
            throw new ResourceNotFoundException();
        }

        if (Objects.nonNull(video.getUser())
            && video.getUser().getId().equals(userId)) {
            return updateVideoInformation(video, likedUserList, dislikedUserList, likeOrDisLike);
        }

        // if the user is already exist in the likedUserList who press the like button
        // then we decrease the like count and remove the user from likedUserList.
        if(likedUserList.contains(loginUser.get())
            && likeOrDisLike.equals(LikeOrDislike.LIKE)) {
            likedUserList.remove(loginUser.get());
            video.setLikeCount(video.getLikeCount() - 1);
            return updateVideoInformation(video, likedUserList, dislikedUserList, likeOrDisLike);
        }

        // if the user is not exist in the likedUserList who press the like button but exist in dislikedUserList
        // then we decrease the dislike count and remove the user from the disLikedUserList
        // after increase the like count and add the user in the likedUserList
        if(!likedUserList.contains(loginUser.get())
            && dislikedUserList.contains(loginUser.get())
            && likeOrDisLike.equals(LikeOrDislike.LIKE)) {
            dislikedUserList.remove(loginUser.get());
            likedUserList.add(loginUser.get());
            video.setDislikeCount(video.getDislikeCount() - 1);
            video.setLikeCount(video.getLikeCount() + 1);
            return updateVideoInformation(video, likedUserList, dislikedUserList, likeOrDisLike);
        }

        // if the user is not exist in both likedUserList and dislikedUserList who press the like button
        // then we increase the like count and add the user in the likedUserList.
        if(!likedUserList.contains(loginUser.get())
            && !dislikedUserList.contains(loginUser.get())
            && likeOrDisLike.equals(LikeOrDislike.LIKE)) {
            likedUserList.add(loginUser.get());
            video.setLikeCount(video.getLikeCount() + 1);
            return updateVideoInformation(video, likedUserList, dislikedUserList, likeOrDisLike);
        }

        // if the user is already exist in the dislikedUserList who press the dislike button
        // then we decrease the dislike count and remove the user from dislikedUserList.
        if(dislikedUserList.contains(loginUser.get())
            && likeOrDisLike.equals(LikeOrDislike.DISLIKE)) {
            dislikedUserList.remove(loginUser.get());
            video.setDislikeCount(video.getDislikeCount() - 1);
            return updateVideoInformation(video, likedUserList, dislikedUserList, likeOrDisLike);
        }

        // if the user is not exist in the dislikedUserList who press the dislike button but exist in likedUserList
        // then we decrease the like count and remove the user from the likedUserList
        // after increase the dislike count and add the user in the dislikedUserList
        if(!dislikedUserList.contains(loginUser.get())
            && likedUserList.contains(loginUser.get())
            && likeOrDisLike.equals(LikeOrDislike.DISLIKE)) {
            likedUserList.remove(loginUser.get());
            video.setDislikeCount(video.getDislikeCount() + 1);
            dislikedUserList.add(loginUser.get());
            video.setLikeCount(video.getLikeCount() - 1);
            return updateVideoInformation(video, likedUserList, dislikedUserList, likeOrDisLike);
        }

        // if the user is not exist in both likedUserList and dislikedUserList who press the dislike button
        // then we increase the dislike count and add the user in the dislikedUserList.
        dislikedUserList.add(loginUser.get());
        video.setDislikeCount(video.getDislikeCount() + 1);
        return updateVideoInformation(video, likedUserList, dislikedUserList, likeOrDisLike);
    }

    @Override
    public void addNewVideo(String title,
                            String url,
                            Long id) {
        if(Objects.isNull(videoRepository.duplicateVideoCheck(url))) {
            Video video = new Video();
            video.setTitle(title);
            video.setUrl(url);
            String videoId = extractVideoId(url);
            video.setVideoId(videoId);
            video.setUser(userRepository.fetchUserById(id));
            videoRepository.save(video);
        }
        else{
            throw new ResourceNotFoundException(id, url);
        }
    }

    @Override
    @Transactional
    public void viewCountUpdate(String videoId, Long id) {
        Video video = videoRepository.getVideoByVideoId(videoId, id);
        if(Objects.isNull(video)){
            throw new ResourceNotFoundException();
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(Objects.isNull(authentication) || authentication instanceof AnonymousAuthenticationToken) {
            video.setViewCount(video.getViewCount() + 1);
            videoRepository.save(video);
            return;
        }

        if(Objects.isNull(video.getUser())) {
            throw new ResourceNotFoundException();
        }

        video.setViewCount(video.getViewCount() + 1);
        videoRepository.save(video);
    }

    @Override
    @Transactional
    public void updateVideo(String videoId,
                            Long id,
                            String title,
                            String url) {
        Video video = videoRepository.getVideoByVideoId(videoId, id);
        if(Objects.isNull(video)){
            throw new ResourceNotFoundException();
        }
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
    public ResponseDTO getDetails(String videoId, Long id) {
        Video video = videoRepository.getVideoByVideoId(videoId, id);
        if(Objects.isNull(video)) {
            throw new ResourceNotFoundException();
        }

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setLikeCount(video.getLikeCount());
        responseDTO.setDislikeCount(video.getDislikeCount());
        responseDTO.setLikedUsers(video.getLikedUser());
        responseDTO.setDislikedUsers(video.getDislikedUser());
        return responseDTO;
    }

    private ResponseDTO updateVideoInformation(Video video,
                                             List<User> likedUserList,
                                             List<User> dislikedUserList,
                                             LikeOrDislike likeOrDislike) {
        video.setLikedUser(likedUserList);
        video.setDislikedUser(dislikedUserList);
        video.setLikeOrDislike(likeOrDislike);
        videoRepository.save(video);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setLikedUsers(likedUserList);
        responseDTO.setDislikedUsers(dislikedUserList);
        responseDTO.setLikeCount(video.getLikeCount());
        responseDTO.setDislikeCount(video.getDislikeCount());
        return responseDTO;
    }
}
