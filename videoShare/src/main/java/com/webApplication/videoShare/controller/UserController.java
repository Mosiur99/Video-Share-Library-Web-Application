package com.webApplication.videoShare.controller;

import com.webApplication.videoShare.Entity.User;
import com.webApplication.videoShare.Entity.Video;
import com.webApplication.videoShare.repository.UserRepository;
import com.webApplication.videoShare.repository.VideoRepository;
import com.webApplication.videoShare.service.UserService;
import com.webApplication.videoShare.service.UserServiceImpl;
import com.webApplication.videoShare.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserService userService;

    @GetMapping("/userSignup")
    public String signupForm(){
        return "userSignup";
    }

    @PostMapping("/userSignup")
    public String signupSubmit(@RequestParam String username, @RequestParam String email, @RequestParam String password){
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);
        return "signupSuccess";
    }

    @GetMapping("/userLogin")
    public String loginForm(){
        return "userLogin";
    }

    @PostMapping("/userLogin")
    public String loginSubmit(@RequestParam String username, @RequestParam String email, @RequestParam String password){
        if(userService.validUser(username, email, password)){
            return "userDashboard";
        }
        else{
            return "InValid User";
        }
    }

    @GetMapping("/userDashboard")
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

    @PostMapping("/addVideo")
    public String addNewVideo(@RequestParam String title, @RequestParam String url){
        Video video = new Video();
        video.setTitle(title);
        video.setUrl(url);
        String videoId = videoService.extractVideoId(url);
        video.setVideoId(videoId);
        videoRepository.save(video);
        return "videoAddedSuccess";
    }

    @GetMapping("/addVideo")
    public String addVideo(){
        return "addVideo";
    }

    @GetMapping("/videoDetails/{videoId}")
    public String videoDetails(@PathVariable String videoId, Model model){
        Video video = videoService.singleVideoDetails(videoId);
        model.addAttribute("video", video);
        return "videoDetails";
    }

    @PostMapping("/like/{videoId}")
    public String likeVideo(@PathVariable String videoId){
        videoService.incrementLikeCount(videoId);
        return "redirect:/videoDetails/{videoId}";
    }

    @PostMapping("/dislike/{videoId}")
    public String dislikeVideo(@PathVariable String videoId){
        videoService.incrementDislikeCount(videoId);
        return "redirect:/videoDetails/{videoId}";
    }
}
