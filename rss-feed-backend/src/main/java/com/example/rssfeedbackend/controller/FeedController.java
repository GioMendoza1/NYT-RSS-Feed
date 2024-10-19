package com.example.rssfeedbackend.controller;

import com.example.rssfeedbackend.model.Feed;
import com.example.rssfeedbackend.service.FeedService;
import com.rometools.rome.io.FeedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    /**
     * URL: /api/feed
     * Purpose: Retrieves the header and list of items from the RSS feed XML
     * @return Feed JSON Object
     */
    @GetMapping(value="/feed", produces="application/json")
    public ResponseEntity<Feed> getFeed(@RequestParam(value = "lang", defaultValue = "en") String lang) throws FeedException, IOException {
        return ResponseEntity.ok().body(feedService.getFeed(lang));
    }
}
