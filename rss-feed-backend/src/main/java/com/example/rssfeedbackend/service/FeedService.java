package com.example.rssfeedbackend.service;

import com.deepl.api.DeepLException;
import com.deepl.api.TextResult;
import com.example.rssfeedbackend.model.Feed;
import com.example.rssfeedbackend.model.FeedItem;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.RequiredArgsConstructor;
import org.jdom2.Element;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import com.deepl.api.Translator;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final Environment env;
    private final Translator translator;

    @Cacheable(value = "feed")
    public Feed getFeed(String language) throws IOException, FeedException {
        URL url = new URL(env.getProperty("rss.feed.url"));
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed rssFeed = input.build(new XmlReader(url));

        List<FeedItem> feedItemList = new ArrayList<>();
        for (SyndEntry entry : rssFeed.getEntries()) {
            for (Element content : entry.getForeignMarkup()) {
                // Only adding items from rssFeed that contain an image
                if (content.getName().equals("content")) {
                    feedItemList.add(FeedItem.builder()
                            .title(entry.getTitle())
                            // Ensuring UTC time zone is maintained and formatting (Ex. Aug. 14, 2022)
                            .date(LocalDateTime.ofInstant(entry.getPublishedDate().toInstant(), ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("MMM. dd, yyyy")))
                            .description(entry.getDescription().getValue())
                            .author(entry.getAuthor().toUpperCase())
                            .contentUrl(entry.getLink())
                            .imageUrl(content.getAttribute("url").getValue())
                            .build()
                    );
                }
            }
        }

        // Sort each feedItemList by its corresponding published date
        sortFeed(feedItemList);

        if (language.equals("es")) {
            translateFeed(feedItemList, language);
        }

        return Feed.builder()
                .logoUrl(rssFeed.getImage().getUrl())
                // Ensuring UTC time zone is maintained and formatting (Ex. Mon, 15 Aug 2022)
                .publishingDate(LocalDateTime.ofInstant(rssFeed.getPublishedDate().toInstant(), ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("EEE, dd MMM yyyy")))
                .feedItems(feedItemList)
                .build();
    }

    public void sortFeed(List<FeedItem> feedItemList) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM. dd, yyyy");
        // Comparator that parses and compares dates in descending order
        feedItemList.sort((o1, o2) -> {
            try {
                LocalDate date1 = LocalDate.parse(o1.getDate(), formatter);
                LocalDate date2 = LocalDate.parse(o2.getDate(), formatter);
                return date2.compareTo(date1);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid date format", e);
            }
        });
    }

    public void translateFeed(List<FeedItem> feedItemList, String translationLanguage) {
        for (FeedItem feedItem : feedItemList) {
            feedItem.setTitle(translateText(feedItem.getTitle(), translationLanguage));
            feedItem.setDescription(translateText(feedItem.getDescription(), translationLanguage));
        }
    }

    private String translateText(String text, String targetLanguage) {
        try {
            TextResult result = translator.translateText(text, null, targetLanguage);
            return result.getText();
        } catch (DeepLException | InterruptedException e) {
            throw new IllegalArgumentException("Translation failed", e);
        }
    }
}
