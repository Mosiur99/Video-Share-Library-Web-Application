package com.webApplication.videoShare.controller;

import com.webApplication.videoShare.entity.User;
import com.webApplication.videoShare.entity.Video;
import com.webApplication.videoShare.repository.VideoRepository;
import com.webApplication.videoShare.service.UserService;
import com.webApplication.videoShare.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class VideoController {

    @Autowired
    private UserService userService;
    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoRepository videoRepository;

    @PostMapping("/user/addVideo")
    public String addNewVideo(@RequestParam String title, @RequestParam String url){
        videoService.newVideoAdded(title, url, userService.fetchUserId());
        return "redirect:/user/userDashboard";
    }

    @GetMapping("/user/addVideo")
    public String addVideo(Model model){
        User user = userService.singleUserDetails(userService.fetchUserId());
        model.addAttribute("user", user);
        return "addVideo";
    }

    @PostMapping("/user/edit/{id}")
    public String editThisVideo(@PathVariable Long id, @RequestParam String title, @RequestParam String url){
        videoService.updateVideo(id, title, url);
        return "redirect:/user/userDashboard";
    }

    @GetMapping("/user/edit/{id}")
    public String editVideo(@PathVariable Long id, Model model){
        Video video = videoRepository.getReferenceById(id);
//        User user = userService.singleUserDetails(userService.fetchUserId());
        model.addAttribute("video", video);
        return "editVideo";
    }

    @GetMapping("/user/videoDetails/{videoId}")
    public String videoDetails(@PathVariable String videoId, Model model){
        Video video = videoService.singleVideoDetails(videoId);
        model.addAttribute("video", video);
        videoService.viewCountUpdate(videoId);
        return "videoDetails";
    }

    @PostMapping("/user/like/{videoId}")
    @ResponseBody
    public ResponseEntity<Long> likeVideo(@PathVariable String videoId){
        Long likeCount = videoService.updateLikeCount(videoId);
        return ResponseEntity.ok(likeCount);
    }


    @PostMapping("/user/dislike/{videoId}")
    public ResponseEntity<Long> dislikeVideo(@PathVariable String videoId){
        Long dislikeCount = videoService.updateDislikeCount(videoId);
        return ResponseEntity.ok(dislikeCount);
    }

    @GetMapping("/view/{videoId}")
    public String viewVideo(@PathVariable String videoId, Model model){
        Video video = videoService.singleVideoDetails(videoId);
        model.addAttribute("video", video);
        videoService.viewCountUpdate(videoId);
        return "view";
    }

}
