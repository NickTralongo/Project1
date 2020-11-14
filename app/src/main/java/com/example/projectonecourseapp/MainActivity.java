package com.example.projectonecourseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.projectonecourseapp.db.AppDatabase;

public class MainActivity extends AppCompatActivity {

    public static MainActivity instance = null;
    public static String username = null;

    /**
     * Main Landing page for the application
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = this;

        Log.d("MainActivity", "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar  = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // load database
        AppDatabase.getAppDatabase(MainActivity.this).loadData(this);

        Button login_button = findViewById(R.id.login_button);
        login_button.setOnClickListener(v -> {
            Log.d("MainActivity", "onClick for login called");
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        Button create_account_button = findViewById(R.id.create_account_button);
        create_account_button.setOnClickListener(v -> {
            Log.d("MainActivity", "onClick for create account called");

            Intent intent = new Intent(MainActivity.this, CreateAccountActivity.class);
            startActivity(intent);
        });
    }
}
