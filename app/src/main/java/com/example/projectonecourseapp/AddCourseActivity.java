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

public class AddCourseActivity extends AppCompatActivity {

    public static AddCourseActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = this;

        Log.d("AddCourseActivity", "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button submit_button = findViewById(R.id.submit);
        submit_button.setOnClickListener(v -> checkInputs());
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

    /**
     *  Function used to check inputs of the Edit Texts
     */
    public void checkInputs() {
        EditText course_name = findViewById(R.id.course_name);
        EditText course_id = findViewById(R.id.course_id);
        EditText instructor_name = findViewById(R.id.instructor_name);
        EditText start_date = findViewById(R.id.start_date);
        EditText end_date = findViewById(R.id.end_date);

        CourseAppDAO dao = AppDatabase.getAppDatabase(AddCourseActivity.this).getCourseDao();

        String course_name_ = course_name.getText().toString();
        String course_id_ = course_id.getText().toString();
        String instructor_ = instructor_name.getText().toString();
        String start_ = start_date.getText().toString();
        String end_ = end_date.getText().toString();

        if(course_name_.isEmpty()) {
            alert("Error", "No Course Name entered");
            return;
        }
        else if (!checkUniqueCourseName(course_name_)) {
            alert("Error", "Course Name already exists");
            return;
        }

        if(course_id_.isEmpty()) {
            alert("Error","No Course ID entered");
            return;
        }
        else if (!checkUniqueCourseId(course_id_)) {
            alert("Error","Course ID already exists");
            return;
        }

        if(instructor_.isEmpty()) {
            alert("Error", "No Instructor Name entered");
            return;
        }

        if(start_.isEmpty()) {
            alert("Error","No Start Date entered");
            return;
        }

        if(end_.isEmpty()) {
            alert("Error","No End Date entered");
            return;
        }

        Course course = new Course(course_name_, course_id_, instructor_, start_, end_, MainActivity.username);
        dao.addCourse(course);

        Log.d("AddCourseActivity", "course added to user");
        alert("Success!", String.format("Course added: %s %s", course.getCourseId(), course.getDescription()));
    }

    /**
     * Function used to check the Course Name Edit Text to make sure the same name doesn't already exist in the database
     *
     * @param courseName The name entered in the Edit Text
     *
     * @return True if the name is unique, false if not
     */
    private Boolean checkUniqueCourseName(String courseName) {

        CourseAppDAO mProjectDao = AppDatabase.getAppDatabase(AddCourseActivity.this).getCourseDao();

        int counter = mProjectDao.countCourseName(courseName);

        return counter <= 0;
    }

    /**
     * Function used to check the Course id Edit Text to make sure the same name doesn't already exist in the database
     *
     * @param courseId The course id entered in the Edit Text
     *
     * @return True if the id is unique, false if not
     */
    private Boolean checkUniqueCourseId(String courseId) {

        CourseAppDAO mProjectDao = AppDatabase.getAppDatabase(AddCourseActivity.this).getCourseDao();

        int counter = mProjectDao.countCourseId(courseId);

        return counter <= 0;
    }
}