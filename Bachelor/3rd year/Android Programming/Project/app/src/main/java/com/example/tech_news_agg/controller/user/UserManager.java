package com.example.tech_news_agg.controller.user;

import android.content.SharedPreferences;

import com.example.tech_news_agg.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserManager {

    private UserManager() {};

    private static Integer activeUserId;
    private static HashMap<Integer, User> userStore;

    public static void loadUserData(Integer activeUserId, String userStoreJson) {
        Gson gson = new Gson();
        Type hashMapType = new TypeToken<HashMap<Integer, User>>(){}.getType();
        UserManager.activeUserId = activeUserId;

        if (userStoreJson == null) {
            userStore = new HashMap<>();
        } else {
            UserManager.userStore = gson.fromJson(userStoreJson, hashMapType);
        }

    }

    public static void loadFromSharedPreferences(SharedPreferences preferences) {
        Integer activeUserId = preferences.getInt("activeUserId", 0);
        String userStoreJson = preferences.getString("userStoreJson", null);
        UserManager.loadUserData(activeUserId, userStoreJson);
    }

    public static void saveSharedPreferences(SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        Integer activeUserId = UserManager.getActiveUserId();
        String userStoreJson = UserManager.getUserStoreJson();

        editor.putInt("activeUserId", activeUserId);
        editor.putString("userStoreJson", userStoreJson);
        editor.apply();
    }

    public static User addNewUser(String username) {
        Integer newUserId = 0;
        for (Integer id : userStore.keySet()) {
            if (id > newUserId) {
                newUserId = id;
            }
        }
        newUserId += 1;
        User newUser = new User(newUserId, username);
        
        userStore.put(newUserId, newUser);
        return newUser;
    }

    public static List<User> getUsers() {
        List<User> result = new ArrayList<>();

        for (Integer id : userStore.keySet()) {
            result.add(userStore.get(id));
        }

        return result;
    }

    public static User getActiveUser() {
        return userStore.get(activeUserId);
    }

    public static Integer getActiveUserId() {
        return activeUserId;
    }

    public static void setActiveUserId(Integer activeUserId) {
        UserManager.activeUserId = activeUserId;
    }

    public static HashMap<Integer, User> getUserStore() {
        return userStore;
    }

    public static void setUserStore(HashMap<Integer, User> userStore) {
        UserManager.userStore = userStore;
    }

    public static String getUserStoreJson() {
        Gson gson = new Gson();
        return gson.toJson(userStore);
    }
}
