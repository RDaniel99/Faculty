package com.example.tech_news_agg.controller.feed.item;

import com.example.tech_news_agg.controller.feed.metadata.RssFeedMetadataStore;
import com.example.tech_news_agg.controller.user.UserManager;
import com.example.tech_news_agg.model.RssFeedItem;
import com.example.tech_news_agg.model.RssFeedMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class RssFeedLoader {

    public static List<RssFeedItem> loadFeedForCurrentUser() {
        List<RssFeedMetadata> subscribedFeeds = extractSubscribedFeeds();
        return extractRssFeedItems(subscribedFeeds);
    }

    private static List<RssFeedItem> extractRssFeedItems(List<RssFeedMetadata> subscribedFeeds) {
        List<RssFeedItem> result = new ArrayList<>();

        try {
            for (RssFeedMetadata currentMetadata : subscribedFeeds) {
                List<RssFeedItem> currentFeedItems = new RssFeedItemExtractor().execute(currentMetadata).get();
                result.addAll(currentFeedItems);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static List<RssFeedMetadata> extractSubscribedFeeds() {
        Set<Integer> subscribedFeedsIds = UserManager.getActiveUser().getFeedSubscriptionList();
        List<RssFeedMetadata> result = new ArrayList<>();

        for (Integer id : subscribedFeedsIds) {
            result.add(RssFeedMetadataStore.getRssFeedStore().get(id));
        }

        return result;
    }
}
