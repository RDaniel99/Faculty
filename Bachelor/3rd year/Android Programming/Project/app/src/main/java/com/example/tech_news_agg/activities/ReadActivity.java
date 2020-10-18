package com.example.tech_news_agg.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tech_news_agg.R;
import com.example.tech_news_agg.controller.feed.item.RssFeedItemAdapter;
import com.example.tech_news_agg.controller.feed.item.RssFeedLoader;
import com.example.tech_news_agg.model.RssFeedItem;

public class ReadActivity extends AppCompatActivity {

    private ListView rssFeedItemListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        rssFeedItemListView = findViewById(R.id.rss_feed_list);

        rssFeedItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                RssFeedItem selectedItem = (RssFeedItem) adapterView.getItemAtPosition(position);
                openBrowser(selectedItem.getLink());
            }
        });
    }

    private void openBrowser(String link) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(link));
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        updateFeedListView();
        super.onStart();
    }

    private void updateFeedListView() {
        RssFeedItemAdapter rssFeedItemAdapter = new RssFeedItemAdapter(this, RssFeedLoader.loadFeedForCurrentUser());
        rssFeedItemListView.setAdapter(rssFeedItemAdapter);
    }
}
