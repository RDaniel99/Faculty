package com.example.tech_news_agg.controller.user;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tech_news_agg.R;
import com.example.tech_news_agg.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {

    private Context context;
    private List<User> userList = new ArrayList<>();

    public UserAdapter(@NonNull Context context, List<User> list) {
        super(context, 0, list);

        this.context = context;
        this.userList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.user_row, parent, false);
        }

        User currentUser = userList.get(position);

        TextView name = listItem.findViewById(R.id.textView_userName);
        name.setText(currentUser.getName());

        return listItem;
    }
}
