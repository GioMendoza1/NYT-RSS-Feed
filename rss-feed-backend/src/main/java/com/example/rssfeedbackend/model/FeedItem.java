package com.example.rssfeedbackend.model;

import lombok.Getter;
import lombok.Builder;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FeedItem {
    private String title;
    private String date;
    private String description;
    private String author;
    private String contentUrl;
    private String imageUrl;
}