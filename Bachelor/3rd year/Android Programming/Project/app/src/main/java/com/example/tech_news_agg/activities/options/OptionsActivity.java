package com.example.tech_news_agg.activities.options;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.tech_news_agg.R;
import com.example.tech_news_agg.activities.MainActivity;
import com.example.tech_news_agg.activities.options.OptionsSelectFeedsActivity;
import com.example.tech_news_agg.activities.options.OptionsSelectUserActivity;
import com.example.tech_news_agg.controller.user.UserManager;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Button backButton = (Button) findViewById(R.id.options_back_button);
        Button selectUserButton = (Button) findViewById(R.id.options_change_user_button);
        Button selectFeedsButton = (Button) findViewById(R.id.options_select_feed_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        selectUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadOptionsSelectUserActivity();
            }
        });

        selectFeedsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadOptionsSelectFeedsActivity();
            }
        });

        selectFeedsButton.setEnabled(UserManager.getActiveUserId() != 0);
    }

    private void goBack() {
        saveUserData();
        Intent playIntent = new Intent(this, MainActivity.class);
        this.startActivity(playIntent);
    }

    private void saveUserData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(String.valueOf(R.string.preference_file_key), 0);
        UserManager.saveSharedPreferences(pref);
    }

    private void loadOptionsSelectUserActivity() {
        Intent playIntent = new Intent(this, OptionsSelectUserActivity.class);
        this.startActivity(playIntent);
    }

    private void loadOptionsSelectFeedsActivity() {
        Intent playIntent = new Intent(this, OptionsSelectFeedsActivity.class);
        this.startActivity(playIntent);
    }
}
