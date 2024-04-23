package com.webApplication.videoShare.controller;

import com.webApplication.videoShare.entity.User;
import com.webApplication.videoShare.entity.Video;
import com.webApplication.videoShare.repository.VideoRepository;
import com.webApplication.videoShare.service.UserService;
import com.webApplication.videoShare.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/user/edit")
    public String editThisVideo(){
//        videoService.newVideoAdded(title, url, userService.fetchUserId());
        return "redirect:/user/userDashboard";
    }

    @GetMapping("/user/edit")
    public String editVideo(){
//        User user = userService.singleUserDetails(userService.fetchUserId());
//        model.addAttribute("user", user);
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
    public String likeVideo(@PathVariable String videoId, Model model){
        videoService.incrementLikeCount(videoId);
        return "redirect:/user/videoDetails/{videoId}";
//        Video video = videoService.singleVideoDetails(videoId);
//        model.addAttribute("video", video);
    }

    @PostMapping("/user/dislike/{videoId}")
    public String dislikeVideo(@PathVariable String videoId){
        videoService.incrementDislikeCount(videoId);
        return "redirect:/user/videoDetails/{videoId}";
    }

    @GetMapping("/view/{videoId}")
    public String viewVideo(@PathVariable String videoId, Model model){
        Video video = videoService.singleVideoDetails(videoId);
        model.addAttribute("video", video);
        videoService.viewCountUpdate(videoId);
        return "view";
    }

}
