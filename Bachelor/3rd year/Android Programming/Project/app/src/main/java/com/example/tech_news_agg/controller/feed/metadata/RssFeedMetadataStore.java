package com.example.tech_news_agg.controller.feed.metadata;

import com.example.tech_news_agg.model.RssFeedMetadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RssFeedMetadataStore {

    private static HashMap<Integer, RssFeedMetadata> rssFeedStore;

    public static List<RssFeedMetadata> getStoreAsList() {
        if (rssFeedStore == null) {
            initStore();
        }

        List<RssFeedMetadata> result = new ArrayList<>();

        for (Integer id : rssFeedStore.keySet()) {
            result.add(rssFeedStore.get(id));
        }

        return result;
    }

    public static HashMap<Integer, RssFeedMetadata> getRssFeedStore() {
        if (rssFeedStore == null) {
            initStore();
        }

        return rssFeedStore;
    }

    private static void initStore() {
        rssFeedStore = new HashMap<>();
        rssFeedStore.put(1, new RssFeedMetadata(1, "TechCrunch", "http://feeds.feedburner.com/Techcrunch"));
        rssFeedStore.put(2, new RssFeedMetadata(2, "Wired", "https://www.wired.com/feed"));
        rssFeedStore.put(3, new RssFeedMetadata(3, "NY Times Technology", "https://rss.nytimes.com/services/xml/rss/nyt/Technology.xml"));
        rssFeedStore.put(4, new RssFeedMetadata(4, "MacWorld", "http://rss.macworld.com/macworld/feeds/main"));
        rssFeedStore.put(5, new RssFeedMetadata(5, "HowToGeek", "https://feeds.howtogeek.com/HowToGeek"));
    }
}
