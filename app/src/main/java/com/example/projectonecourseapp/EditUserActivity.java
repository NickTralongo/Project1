package com.example.projectonecourseapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.projectonecourseapp.db.AppDatabase;
import com.example.projectonecourseapp.db.Course;
import com.example.projectonecourseapp.db.CourseAppDAO;
import com.example.projectonecourseapp.db.User;

import java.util.ArrayList;
import java.util.List;

public class EditUserActivity extends AppCompatActivity {


    User user = null;
    List<Course> courses_taken = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CourseAppDAO dao = AppDatabase.getAppDatabase(EditUserActivity.this).getCourseDao();
        user = dao.getUserByUsername(MainActivity.username);

        Log.d("EditUserActivity", String.format("username: %s", MainActivity.username));

        Button edit_button = findViewById(R.id.edit_button);
        edit_button.setOnClickListener(view -> {
            checkInputs();
        });

    }

    /**
     *  Function used to check inputs of the Edit Texts
     */
    public void checkInputs() {
        EditText first_name = findViewById(R.id.edit_first_name);
        EditText last_name = findViewById(R.id.edit_last_name);
        EditText username = findViewById(R.id.edit_username);
        EditText password = findViewById(R.id.edit_password);

        String first_name_ = first_name.getText().toString();
        String last_name_ = last_name.getText().toString();
        String username_ = username.getText().toString();
        String password_ = password.getText().toString();

        if(first_name_.isEmpty()) {
            alert("Error", "You need to have a first name.");
            return;
        }

        if(last_name_.isEmpty()) {
            alert("Error", "You need to have a last name.");
            return;
        }

        if(username_.isEmpty()) {
            alert("Error", "You need to have a username.");
            return;
        }

        if(password_.isEmpty()) {
            alert("Error", "You really need a password.");
            return;
        }
        CourseAppDAO dao = AppDatabase.getAppDatabase(EditUserActivity.this).getCourseDao();

        // add if username already exists
        User temp = dao.getUserByUsername(username_);
        if(temp == null) {
            courses_taken = dao.getCoursesTaken(user.getUserName());
            user.setFirstName(first_name_);
            user.setLastName(last_name_);
            user.setUserName(username_);
            user.setPassword(password_);

            dao.updateUser(user);

            for(Course course: courses_taken) {
                course.setUsername(user.getUserName());
                dao.updateCourse(course);
            }

            alert("Success!", "You have updated your profile!");
        } else {
            alert("Error", "Username is already taken, try another.");
        }

    }

    /**
     * Function used to send alerts to user if an error is brought up
     *
     * @param title What the alert is referring to
     * @param message What is wrong with said topic
     */
    public void alert(String title, String message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setPositiveButton("Okay", (dialogInterface, i) -> {
            if(title.equals("Success!")) {
                finish();
            } else {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setMessage(message);
        dialog.show();
    }
}