package com.example.projectonecourseapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.projectonecourseapp.db.AppDatabase;
import com.example.projectonecourseapp.db.Assignment;
import com.example.projectonecourseapp.db.Course;
import com.example.projectonecourseapp.db.CourseAppDAO;

import java.util.ArrayList;
import java.util.List;

public class DisplayCourseActivity extends AppCompatActivity {

    public static DisplayCourseActivity instance = null;

    Assignment assignment = null;
    Course course = null;
    List<Assignment> assignments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = this;

        Log.d("DisplayCourseActivity", "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_course);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String course_Id = getIntent().getStringExtra("course");
        DisplayUserCourseActivity.username = getIntent().getStringExtra("username");
        assert course_Id != null;

        TextView message = findViewById(R.id.message);
        message.setText("");

        Button add_assignment = findViewById(R.id.add_assignment_button);
        add_assignment.setOnClickListener(view -> {
            Intent intent = new Intent(DisplayCourseActivity.this, AddAssignmentActivity.class);
            intent.putExtra("course", course_Id);
            startActivity(intent);
            finish();
        });

        Button delete_assignment = findViewById(R.id.delete_assignment_button);
        delete_assignment.setOnClickListener(view -> alert("Instructions",
                "Tap on assignment to edit.\nPress and hold on assignment to delete."));

        CourseAppDAO dao = AppDatabase.getAppDatabase(DisplayCourseActivity.this).getCourseDao();
        course = dao.getCourseByCourseId(course_Id);
        message.setText(String.format("%s %s\n", course.getCourseId(), course.getDescription()));

        // get assignments
        assignments = dao.getAllAssignmentsByCourseId(course_Id);
        if(assignments.isEmpty()) {
            message.append("No assignments added yet");
        } else {
            updateList();
        }
    }

    /**
     * Updates the list in the list view to see assignments
     */
    public void updateList() {
        ListView lv = findViewById(R.id.list_view);
        List<String> rows = new ArrayList<>();
        for(Assignment assignment: assignments) {
            rows.add(getAssignmentDetails(assignment));
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, rows);
        lv.setAdapter(arrayAdapter);

        // users can edit assignment
        lv.setOnItemClickListener((parent, view, i, l) -> {
            String selected_item = (String) parent.getItemAtPosition(i);
            String selected_temp = selected_item.split("\n")[0];
            editAssignment(selected_temp);
        });
        // delete assignments
        lv.setOnItemLongClickListener((parent, view, i, l) -> {
            String selected_item = (String) parent.getItemAtPosition(i);
            String selected_temp = selected_item.split("\n")[0];
            deleteAssignment(selected_temp);
            return true;
        });
    }

    /**
     * Function allowing the user to edit assignments
     *
     * @param assignment_details The assignment the user wishes to edit
     */
    public void editAssignment(String assignment_details) {
        CourseAppDAO dao = AppDatabase.getAppDatabase(this).getCourseDao();
        assignment = dao.getAssignmentByDetails(assignment_details);
        if(assignment != null) {

            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(55, 5, 15, 5);

            final EditText description = new EditText(this);
            description.setHint("Description about assignment");
            layout.addView(description);

            final EditText max_score = new EditText(this);
            max_score.setHint("Enter max score");
            max_score.setInputType(InputType.TYPE_CLASS_NUMBER);
            layout.addView(max_score);

            final EditText earned_score = new EditText(this);
            earned_score.setHint("Enter your earned score");
            earned_score.setInputType(InputType.TYPE_CLASS_NUMBER);
            layout.addView(earned_score);

            final EditText assigned_date = new EditText(this);
            assigned_date.setHint("Enter assigned date");
            assigned_date.setInputType(InputType.TYPE_CLASS_DATETIME);
            layout.addView(assigned_date);

            final EditText due_date = new EditText(this);
            due_date.setHint("Enter due date");
            due_date.setInputType(InputType.TYPE_CLASS_DATETIME);
            layout.addView(due_date);

            final EditText category_id = new EditText(this);
            category_id.setHint("HW/Quiz/Project/Exam");
            layout.addView(category_id);

            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("Edit Assignment")
                    .setPositiveButton("Okay", (dialog, which) -> {
                        String description_ = description.getText().toString();
                        String max_score_ = max_score.getText().toString();
                        String earned_score_ = earned_score.getText().toString();
                        String assigned_date_ = assigned_date.getText().toString();
                        String due_date_ = due_date.getText().toString();
                        String category_id_ = category_id.getText().toString();

                        if(description_.isEmpty() || max_score_.isEmpty() || earned_score_.isEmpty()
                                || assigned_date_.isEmpty() || due_date_.isEmpty() || category_id_.isEmpty()) {
                            alert("Error", "Please don't leave any fields blank.");
                            return;
                        }

                        assignment.setDetails(description_);
                        assignment.setMaxScore(max_score_);
                        assignment.setEarnedScore(earned_score_);
                        assignment.setAssignedDate(assigned_date_);
                        assignment.setDueDate(due_date_);
                        assignment.setCategoryId(category_id_);
                        dao.updateAssignment(assignment);

                        alert("Success!", "You have updated your assignment.");
                    })
                    .setNegativeButton("Cancel", (dialog, which) ->
                            alert("Edit Assignment", "No changes were made."))
                    .setOnCancelListener(dialog ->
                            Log.d("DisplayCourseActivity", "No changes made"))
                    .setView(layout);
            AlertDialog alert = builder.create();
            alert.show();

        } else {
            // if somehow Assignment is null, exit
            alert("Error!", "Assignment does not exist!");
            finish();
        }
    }

    /**
     * Function to allow user to delete assignments
     *
     * @param assignment_details The assignment the user wishes to delete
     */
    public void deleteAssignment(String assignment_details) {
        CourseAppDAO dao = AppDatabase.getAppDatabase(this).getCourseDao();
        assignment = dao.getAssignmentByDetails(assignment_details);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to delete this assignment?");
        builder.setMessage(getAssignmentDetails(assignment));
        builder.setPositiveButton("Yes", (dialog, which) -> {
           // delete assignment
           dao.deleteAssignment(assignment);
           alert("Success!", "Your assignment was deleted.");
        });
        builder.setNegativeButton("No", (dialog, which) -> {
            alert("Assignment", "Assignment was not deleted.");
        });
        builder.setOnCancelListener(dialog -> {
           Log.d("DisplayCourseActivity", "onCancelListener");
        });
        AlertDialog alert = builder.create();
        alert.show();
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
        String button_text = "Okay";
        builder.setPositiveButton(button_text, (dialogInterface, i) -> {
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

    /**
     * Function to get the assignment details and attributes
     *
     * @param assignment The assignment the user wishes to see
     *
     * @return The assignment's details and attributes
     */
    public String getAssignmentDetails(Assignment assignment) {
        return String.format("%s\nAssigned: %s\nDue: %s \nScore: %s/%s\nCategory: %s",
                assignment.getDetails(), assignment.getAssignedDate(), assignment.getDueDate(),
                assignment.getEarnedScore(), assignment.getMaxScore(), assignment.getCategoryId());
    }

    // implementing some time
    public void updateDisplayByCategory() {

    }

}