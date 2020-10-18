package com.example.tech_news_agg.activities.options;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tech_news_agg.R;
import com.example.tech_news_agg.activities.MainActivity;
import com.example.tech_news_agg.controller.user.UserAdapter;
import com.example.tech_news_agg.model.User;
import com.example.tech_news_agg.controller.user.UserManager;

public class OptionsSelectUserActivity extends AppCompatActivity {

    private User activeUser;
    private TextView activeUserNameTextView;
    private ListView usersListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_user);


        activeUserNameTextView = findViewById(R.id.user_settings_active_user_text);
        Button addUserButton =  (Button) findViewById(R.id.user_settings_add_button);
        final EditText inputFieldUserName = findViewById(R.id.user_settings_edit_text);
        usersListView = findViewById(R.id.user_list);

        Button applyButton = findViewById(R.id.user_settings_apply_button);

        loadActiveUser();

        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = inputFieldUserName.getText().toString();
                if (!userName.equals("")) {
                    addNewUser(userName);

                }
            }
        });

        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                activeUser = (User) adapterView.getItemAtPosition(position);
                UserManager.setActiveUserId(activeUser.getId());
                updateActiveUserNameTextView();
            }
        });

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToGeneralOptions();
            }
        });
    }

    private void goToGeneralOptions() {
        Intent playIntent = new Intent(this, OptionsActivity.class);
        this.startActivity(playIntent);
    }

    private void addNewUser(String userName) {
        activeUser = UserManager.addNewUser(userName);
        UserManager.setActiveUserId(activeUser.getId());
        updateActiveUserNameTextView();
        updateUserListView();
    }

    private void loadActiveUser() {
        this.activeUser = UserManager.getActiveUser();
        updateActiveUserNameTextView();
    }

    private void updateActiveUserNameTextView() {
        if (activeUser == null) {
            activeUserNameTextView.setText("Active user: n/a");
        } else {
            String username = activeUser.getName();
            activeUserNameTextView.setText("Active user: " + username);
        }
    }

    @Override
    protected void onStart() {
        updateUserListView();
        super.onStart();
    }

    private void updateUserListView() {
        UserAdapter userAdapter = new UserAdapter(this, UserManager.getUsers());
        usersListView.setAdapter(userAdapter);
    }
}
