package com.example.tech_news_agg.activities.options;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tech_news_agg.R;
import com.example.tech_news_agg.controller.feed.metadata.RssFeedMetadataAdapter;
import com.example.tech_news_agg.controller.feed.metadata.RssFeedMetadataStore;
import com.example.tech_news_agg.controller.user.UserManager;

public class OptionsSelectFeedsActivity extends AppCompatActivity {

    private ListView feedMetadataListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_feeds);

        TextView activeUserNameTextView = findViewById(R.id.feed_settings_active_user_text);
        feedMetadataListView = findViewById(R.id.feed_metadata_list);
        
        activeUserNameTextView.setText("Active user: " + UserManager.getActiveUser().getName());
    }

    @Override
    protected void onStart() {
        updateFeedMetadataListView();
        super.onStart();
    }

    private void updateFeedMetadataListView() {
        RssFeedMetadataAdapter metadataAdapter = new RssFeedMetadataAdapter(this, RssFeedMetadataStore.getStoreAsList());
        feedMetadataListView.setAdapter(metadataAdapter);
    }
}
