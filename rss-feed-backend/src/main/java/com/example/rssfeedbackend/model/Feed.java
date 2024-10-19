package com.example.rssfeedbackend.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Feed {
    private String logoUrl;
    private String publishingDate;
    private List<FeedItem> feedItems;
}
