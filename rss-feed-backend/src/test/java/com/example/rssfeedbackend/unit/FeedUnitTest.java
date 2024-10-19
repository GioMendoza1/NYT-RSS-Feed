package com.example.rssfeedbackend.unit;

import com.deepl.api.Translator;
import com.example.rssfeedbackend.model.FeedItem;
import com.example.rssfeedbackend.service.FeedService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ExtendWith(MockitoExtension.class)
public class FeedUnitTest {
    FeedService feedService;

    @Mock
    private Environment env;
    @Mock
    private Translator translator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        feedService = new FeedService(env, translator);
    }

    @Test
	void testSortFeedShouldSortDescendingOrder() {
        List<FeedItem> feedItems = Arrays.asList(
                FeedItem.builder().date("Oct. 10, 2023").build(),
                FeedItem.builder().date("Jan. 15, 2022").build(),
                FeedItem.builder().date("May. 23, 2024").build()
        );

        feedService.sortFeed(feedItems);
        Assertions.assertEquals(feedItems.get(0).getDate(), "May. 23, 2024");
        Assertions.assertEquals(feedItems.get(1).getDate(), "Oct. 10, 2023");
	}
}
