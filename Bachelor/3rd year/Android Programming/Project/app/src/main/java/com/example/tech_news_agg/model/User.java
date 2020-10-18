package com.example.tech_news_agg.model;

import android.util.Log;

import com.example.tech_news_agg.controller.user.UserManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class User {

    private Integer id;
    private String name;
    private Set<Integer> feedSubscriptionList;

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.feedSubscriptionList = new LinkedHashSet<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Integer> getFeedSubscriptionList() {
        return feedSubscriptionList;
    }

    public void setFeedSubscriptionList(Set<Integer> feedSubscriptionList) {
        this.feedSubscriptionList = feedSubscriptionList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", feedSubscriptionList=" + feedSubscriptionList +
                '}';
    }
}
