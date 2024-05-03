package com.webApplication.videoShare.controller;

import com.webApplication.videoShare.entity.User;
import com.webApplication.videoShare.entity.Video;
import com.webApplication.videoShare.service.UserService;
import com.webApplication.videoShare.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Controller
public class UserController {

    private final UserService userService;
    private final VideoService videoService;

    @Autowired
    public UserController(UserService userService,
                          VideoService videoService) {
        this.userService = userService;
        this.videoService = videoService;
    }

    @GetMapping("/userSignup")
    public String signupForm() {
        return "userSignup";
    }

    @PostMapping("/userSignup")
    public String signupSubmit(@RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String password) {
        userService.saveNewUser(username, email, password);
        return "signupSuccess";
    }

    @GetMapping("/userLogin")
    public String loginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(Objects.isNull(authentication) || authentication instanceof AnonymousAuthenticationToken){
            return "userLogin";
        }

        return "redirect:/user/userDashboard";
    }

    @PostMapping("/userLogin")
    public String loginSubmit(@RequestParam String email,
                              @RequestParam String password) {
        try{
            userService.isValidUser(email, password);
            return "redirect:/user/userDashboard";
        }catch(Exception exception){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error");
        }
    }

    @GetMapping("/user/userDashboard")
    public String userDashboard(Model model) {
        List<Video> videoList = videoService.getAllVideosByUserId();
        model.addAttribute("videoList", videoList);
        return "userDashboard";
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        List<Video> videoList = videoService.getAllVideos();
        model.addAttribute("videoList", videoList);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "home";
        }

        return "redirect:/user/userHome";
    }

    @GetMapping("/user/userHome")
    public String userHomePage(Model model) {
        List<Video> videoList = videoService.getAllVideos();
        model.addAttribute("videoList", videoList);
        User user = userService.singleUserDetails(userService.fetchUserId());
        model.addAttribute(user);
        return "userHome";
    }
}
