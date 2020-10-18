package com.example.tech_news_agg.controller.feed.metadata;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.tech_news_agg.R;
import com.example.tech_news_agg.controller.user.UserManager;
import com.example.tech_news_agg.model.RssFeedMetadata;
import com.example.tech_news_agg.model.User;

import java.util.ArrayList;
import java.util.List;

public class RssFeedMetadataAdapter extends ArrayAdapter<RssFeedMetadata> {

    private Context context;
    private List<RssFeedMetadata> rssFeedMetadata = new ArrayList<>();

    public RssFeedMetadataAdapter(@NonNull Context context, List<RssFeedMetadata> list) {
        super(context, 0, list);

        this.context = context;
        this.rssFeedMetadata = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.feed_metadata_row, parent, false);
        }

        final RssFeedMetadata currentMetadata = rssFeedMetadata.get(position);

        final CheckBox metadataCheckbox = listItem.findViewById(R.id.feed_checkbox);
        metadataCheckbox.setText(currentMetadata.getName());

        metadataCheckbox.setChecked(UserManager.getActiveUser().getFeedSubscriptionList().contains(currentMetadata.getId()));

        metadataCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    UserManager.getActiveUser().getFeedSubscriptionList().add(currentMetadata.getId());
                } else {
                    UserManager.getActiveUser().getFeedSubscriptionList().remove(currentMetadata.getId());
                }
            }
        });

        return listItem;
    }
}
