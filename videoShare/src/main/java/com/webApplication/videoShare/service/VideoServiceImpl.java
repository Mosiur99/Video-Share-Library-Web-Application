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
public class VideoServiceImpl implements VideoService {

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
        Video video = videoRepository.getVideoById(videoId);
        if(Objects.isNull(video)) {
            throw new ResourceNotFoundException();
        }

        return video;
    }

    @Override
    @Transactional
    public ResponseDTO action(Long id,
                              String videoId,
                              LikeOrDislike likeOrDislike) {
        Long userId = userService.fetchUserId();
        Video video = videoRepository.getVideoById(videoId);
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
            return updateVideoInformation(video, likedUserList, likeOrDislike, dislikedUserList);
        }

        afterAction(loginUser.get(), video, likedUserList, dislikedUserList, likeOrDislike);

        return updateVideoInformation(video, likedUserList, likeOrDislike, dislikedUserList);
    }

    @Override
    @Transactional
    public void addNewVideo(Long id,
                            String url,
                            String title,
                            String previousVideoId) {
        if(!Objects.isNull(videoRepository.duplicateVideoCheck(url, id))) {
            throw new ResourceNotFoundException(id, url);
        }

        Video video = videoRepository.getVideoById(previousVideoId);
        if(Objects.isNull(video)) {
            video = new Video();
        }

        video.setTitle(title);
        video.setUrl(url);
        String videoId = extractVideoId(url);
        video.setVideoId(videoId);
        video.setUser(userRepository.fetchUserById(id));
        video.setLikeCount(0);
        video.setDislikeCount(0);
        video.setViewCount(0);
        video.setLikedUser(null);
        video.setDislikedUser(null);
        videoRepository.save(video);
    }

    @Override
    @Transactional
    public void viewCountUpdate(Long id,
                                String videoId) {
        Video video = videoRepository.getVideoById(videoId);
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
    public ResponseDTO getDetails(Long id,
                                  String videoId) {
        Video video = videoRepository.getVideoById(videoId);
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
                                               LikeOrDislike likeOrDislike,
                                               List<User> dislikedUserList) {
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

    private void afterAction(User user,
                              Video video,
                              List<User> likedUserList,
                              List<User> dislikedUserList,
                              LikeOrDislike likeOrDislike) {
        /**
         * if the user is not exist in the likeUserList and press the like button
         * then we add the user in likedUserList and increase the like count
         */
        if(!likedUserList.contains(user) && !dislikedUserList.contains(user) && likeOrDislike.equals(LikeOrDislike.LIKE)) {
            likedUserList.add(user);
            video.setLikeCount(video.getLikeCount() + 1);
        }

        /**
         * if the user is not exist in the dislikeUserList and press the dislike button
         * then we add the user in dislikedUserList and increase the dislike count
         */
        else if(!likedUserList.contains(user) && !dislikedUserList.contains(user) && likeOrDislike.equals(LikeOrDislike.DISLIKE)) {
            dislikedUserList.add(user);
            video.setDislikeCount(video.getDislikeCount() + 1);
        }

        /**
         * if the user existed in the likeUserList and press the like button
         * then we remove the user from likedUserList and decrease the like count
         */
        else if(likedUserList.contains(user) && likeOrDislike.equals(LikeOrDislike.LIKE)) {
            likedUserList.remove(user);
            video.setLikeCount(video.getLikeCount() - 1);
        }

        /**
         * if the user existed in the dislikeUserList and press the dislike button
         * then we remove the user from dislikedUserList and decrease the dislike count
         */
        else if(dislikedUserList.contains(user) && likeOrDislike.equals(LikeOrDislike.DISLIKE)) {
            dislikedUserList.remove(user);
            video.setDislikeCount(video.getDislikeCount() - 1);
        }

        /**
         * if the user existed in the likeUserList and press the dislike button
         * then we remove the user from likedUserList and decrease the like count
         * and then we add the user in dislikedUserList and increase the dislike count
         */
        else if(likedUserList.contains(user) && likeOrDislike.equals(LikeOrDislike.DISLIKE)) {
            likedUserList.remove(user);
            video.setLikeCount(video.getLikeCount() - 1);
            dislikedUserList.add(user);
            video.setDislikeCount(video.getDislikeCount() + 1);
        }

        /**
         * if the user existed in the dislikeUserList and press the like button
         * then we remove the user from dislikedUserList and decrease the dislike count
         * and then we add the user in likedUserList and increase the like count
         */
        else if(dislikedUserList.contains(user) && likeOrDislike.equals(LikeOrDislike.LIKE)) {
            dislikedUserList.remove(user);
            video.setDislikeCount(video.getDislikeCount() - 1);
            likedUserList.add(user);
            video.setLikeCount(video.getLikeCount() + 1);
        }
    }
}
