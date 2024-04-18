package com.webApplication.videoShare.utility;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YoutubeUtil {

    private static final String YOUTUBE_REGEX = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";

    public static String extractVideoIdFromUrl(String url){
        String videoId = null;
        try{
            Pattern pattern = Pattern.compile(YOUTUBE_REGEX);
            Matcher matcher = pattern.matcher(url);
            if(matcher.find()){
                videoId = matcher.group();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return videoId;

//        System.out.println("Hey i am calling");

//        String vId = null;
//        Pattern pattern = Pattern.compile(
//                "^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$",
//                Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(url);
//        if (matcher.matches()){
//            vId = matcher.group(1);
//        }
//        return vId;
    }
}
