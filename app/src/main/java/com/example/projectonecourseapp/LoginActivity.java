package com.example.projectonecourseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projectonecourseapp.db.AppDatabase;
import com.example.projectonecourseapp.db.CourseAppDAO;
import com.example.projectonecourseapp.db.User;

public class LoginActivity extends AppCompatActivity {

    /**
     * Main Login page for the application
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText username = findViewById(R.id.username);
        final EditText password = findViewById(R.id.password);

        Button login_button = findViewById(R.id.login_button);
        login_button.setOnClickListener(v -> {
            final String username_ = username.getText().toString();
            String password_ = password.getText().toString();
            TextView message = findViewById(R.id.message);

            if (username.getText().toString().equals("") || password.getText().toString().equals("")) {
                message.setText(R.string.missing_user_pass);
                return;
            }

            CourseAppDAO dao = AppDatabase.getAppDatabase(LoginActivity.this).getCourseDao();
            User user = dao.login(username_, password_);
            if(user == null) {
                // unsuccessful login, check is username or password is wrong
                User user_temp = dao.getUserByUsername(username_);
                if(user_temp == null) {
                    // username error
                    message.setText(R.string.incorrect_username);
                } else {
                    // password error
                    message.setText(R.string.incorrect_password);
                }
            } else {
                // successful
                MainActivity.username = username_;
                Intent intent = new Intent(LoginActivity.this, DisplayUserCourseActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Log.d("LoginActivity", "Login canceled");
        finish();
    }
}