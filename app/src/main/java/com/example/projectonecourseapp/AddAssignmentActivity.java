package com.example.projectonecourseapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.projectonecourseapp.db.AppDatabase;
import com.example.projectonecourseapp.db.Assignment;
import com.example.projectonecourseapp.db.CourseAppDAO;


public class AddAssignmentActivity extends AppCompatActivity {

    public static AddAssignmentActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = this;

        Log.d("AddAssignmentActivity", "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button add_assignment = findViewById(R.id.add_assignment_button);
        add_assignment.setOnClickListener(v -> checkInputs());
    }

    /**
     *  Function used to check inputs of the Edit Texts
     */
    public void checkInputs() {
        EditText assignment_details = findViewById(R.id.assignment_details);
        EditText max_score = findViewById(R.id.assignment_max_score);
        EditText earned_score = findViewById(R.id.assignment_earned_score);
        EditText assigned_date = findViewById(R.id.assignment_assign_date);
        EditText due_date = findViewById(R.id.assignment_due_date);
        EditText category = findViewById(R.id.assignment_category);

        String assignment_details_ = assignment_details.getText().toString();
        String max_score_ = max_score.getText().toString();
        String earned_score_ = earned_score.getText().toString();
        String assigned_date_ = assigned_date.getText().toString();
        String due_date_ = due_date.getText().toString();
        String category_ = category.getText().toString();

        if(assignment_details_.isEmpty()) {
            alert("Error", "Please enter assignment details.");
            return;
        }
        if(max_score_.isEmpty()) {
            alert("Error", "Please enter max score.");
            return;
        }
        if(earned_score_.isEmpty()) {
            alert("Error", "Please enter your score");
            return;
        }
        if(assigned_date_.isEmpty()) {
            alert("Error", "Enter when your assignment was assigned.");
            return;
        }
        if(due_date_.isEmpty()) {
            alert("Error", "Enter the assignment's due date.");
            return;
        }
        if(category_.isEmpty()) {
            alert("Error", "Enter assignment category");
            return;
        }

        String course_id = getIntent().getStringExtra("course");

        CourseAppDAO dao = AppDatabase.getAppDatabase(AddAssignmentActivity.this).getCourseDao();
        Assignment assignment = new Assignment(assignment_details_, max_score_, earned_score_, assigned_date_, due_date_, course_id, category_);
        dao.addAssignment(assignment);
        alert("Success!", "You have added a new assignment.");
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