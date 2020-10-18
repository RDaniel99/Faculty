package com.example.tech_news_agg.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tech_news_agg.R;
import com.example.tech_news_agg.activities.options.OptionsActivity;
import com.example.tech_news_agg.controller.user.UserManager;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button readButton = (Button) findViewById(R.id.main_menu_read_button);
        Button optionsButton = (Button) findViewById(R.id.main_menu_options_button);
        Button quitButton = (Button) findViewById(R.id.main_menu_quit_button);

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReadActivity();
            }
        });

        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadOptionsActivity();
            }
        });

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });

        loadUserData();
        readButton.setEnabled(UserManager.getActiveUserId() != 0);

        TextView helloTextView = (TextView) findViewById(R.id.main_menu_hello_text);

        String helloText = UserManager.getActiveUserId() == 0 ? "Hello!" : "Hello, " + UserManager.getActiveUser().getName() + "!";
        helloTextView.setText(helloText);
    }

    private void loadUserData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(String.valueOf(R.string.preference_file_key), 0);
        UserManager.loadFromSharedPreferences(pref);
    }


    private void loadReadActivity() {
        Intent playIntent = new Intent(this, ReadActivity.class);
        this.startActivity(playIntent);
    }

    private void loadOptionsActivity() {
        Intent playIntent = new Intent(this, OptionsActivity.class);
        this.startActivity(playIntent);
    }

}
