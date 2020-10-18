package com.example.tech_news_agg.controller.feed.item;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tech_news_agg.R;
import com.example.tech_news_agg.model.RssFeedItem;

import java.util.ArrayList;
import java.util.List;

public class RssFeedItemAdapter extends ArrayAdapter<RssFeedItem> {

    private Context context;
    private List<RssFeedItem> rssFeedItemList = new ArrayList<>();

    public RssFeedItemAdapter(@NonNull Context context, List<RssFeedItem> list) {
        super(context, 0, list);
        this.context = context;
        this.rssFeedItemList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.feed_item_row, parent, false);
        }

        RssFeedItem currentFeedItem = rssFeedItemList.get(position);

        TextView title = listItem.findViewById(R.id.feed_item_title);
        TextView pubDate = listItem.findViewById(R.id.feed_item_pubDate);
        TextView description = listItem.findViewById(R.id.feed_item_description);

        title.setText(currentFeedItem.getTitle());
        pubDate.setText(currentFeedItem.getPubDate());
        description.setText(currentFeedItem.getDescription());

        return listItem;
    }
}
