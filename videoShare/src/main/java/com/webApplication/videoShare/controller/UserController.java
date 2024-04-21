package com.webApplication.videoShare.controller;

import com.webApplication.videoShare.Entity.User;
import com.webApplication.videoShare.Entity.Video;
import com.webApplication.videoShare.repository.UserRepository;
import com.webApplication.videoShare.repository.VideoRepository;
import com.webApplication.videoShare.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoService videoService;

    @Autowired
//    private UserService userService;

    @GetMapping("/userSignup")
    public String signupForm(){
        return "userSignup";
    }

    @PostMapping("/userSignup")
    public String signupSubmit(@RequestParam String username, @RequestParam String email, @RequestParam String password){
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return "signupSuccess";
    }

    @GetMapping("/userLogin")
    public String loginPage(){
        return "userLogin";
    }

//    @PostMapping("/userLogin")
//    public String loginSubmit(@RequestParam String username, @RequestParam String email, @RequestParam String password){
//
//    }

    @GetMapping("/user/userDashboard")
    public String userDashboard(Model model) {
        List<Video> videoList = videoService.getAllVideos();
        model.addAttribute("videoList", videoList);
        return "userDashboard";
    }

    @GetMapping("/home")
    public String homePage(Model model){
        List<Video> videoList = videoService.getAllVideos();

        model.addAttribute("videoList", videoList);
        return "home";
    }


//    @GetMapping("/videourl")
//    public String getVideoUrl(Model model){
//        List<Object> videosUrl = videoRepository.getVideosUrl();
//        model.addAttribute("videosUrl", videosUrl);
//        return "videourl";
//    }

//    @PostMapping("/userDashboard")
//    public String VideoAddedPage(){
//        return "addVideo";
//    }

    @PostMapping("/user/addVideo")
    public String addNewVideo(@RequestParam String title, @RequestParam String url){
        Video video = new Video();
        video.setTitle(title);
        video.setUrl(url);
        String videoId = videoService.extractVideoId(url);
        video.setVideoId(videoId);
        videoRepository.save(video);
        return "videoAddedSuccess";
    }

    @GetMapping("/user/addVideo")
    public String addVideo(){
        return "addVideo";
    }

    @GetMapping("/user/videoDetails/{videoId}")
    public String videoDetails(@PathVariable String videoId, Model model){
        Video video = videoService.singleVideoDetails(videoId);
        model.addAttribute("video", video);
        return "videoDetails";
    }

    @PostMapping("/user/like/{videoId}")
    public String likeVideo(@PathVariable String videoId){
        videoService.incrementLikeCount(videoId);
        return "redirect:/user/videoDetails/{videoId}";
    }

    @PostMapping("/user/dislike/{videoId}")
    public String dislikeVideo(@PathVariable String videoId){
        videoService.incrementDislikeCount(videoId);
        return "redirect:/user/videoDetails/{videoId}";
    }
}
