package com.example.projectonecourseapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
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
import com.example.projectonecourseapp.db.User;

import java.util.ArrayList;
import java.util.List;

public class DisplayUserCourseActivity extends AppCompatActivity {

    public static DisplayUserCourseActivity instance = null;
    public static String username = null;

    List<Course> courses = new ArrayList<>();
    List<Assignment> assignment_grades = new ArrayList<>();
    Course course = null;
    Double course_grade = null;
    String course_id_temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = this;

        Log.d("DisplayUserCourseActivity", "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user_course);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CourseAppDAO dao = AppDatabase.getAppDatabase(DisplayUserCourseActivity.this).getCourseDao();
        username = MainActivity.username;
        User user = dao.getUserByUsername(username);

        TextView message = findViewById(R.id.message);
        message.setText("");

        if(user.getFirstName() != null) {
            message.setText(String.format("Welcome, %s\n", user.getFirstName()));
        } else {
            message.setText(String.format("Welcome, %s\n", user.getUserName()));
        }

        Button add_course_button = findViewById(R.id.add_course_button);
        add_course_button.setOnClickListener(v -> {
            Intent intent = new Intent(DisplayUserCourseActivity.this, AddCourseActivity.class);
            startActivity(intent);
            finish();
        });

        Button delete_course_button = findViewById(R.id.delette_course_button);
        delete_course_button.setOnClickListener(v -> {
            Intent intent = new Intent(DisplayUserCourseActivity.this, DeleteCourseActivity.class);
            startActivity(intent);
            finish();
        });

        Button edit_user_button = findViewById(R.id.edit_user);
        edit_user_button.setOnClickListener(v -> {
            Intent intent = new Intent(DisplayUserCourseActivity.this, EditUserActivity.class);
            startActivity(intent);
            finish();
        });

        // load the course data
        courses = dao.getCoursesTaken(MainActivity.username);
        if(courses.isEmpty()) {
            message.append("No Courses added yet");
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
        CourseAppDAO dao = AppDatabase.getAppDatabase(this).getCourseDao();
        String grade_temp;
        for(Course course: courses) {
            // get overall assignment grades from course
            assignment_grades = dao.getAllAssignmentsByCourseId(course.getCourseId());
            if(assignment_grades.size() != 0) {
                course_grade = getAverage(assignment_grades);
            }

            if(course_grade == null) {
                grade_temp = "N/A";
            } else {
                grade_temp = String.format("%.2f", course_grade);
                grade_temp += "%";
            }
            rows.add(String.format("Course: %s %s\nInstructor: %s" +
                    "\nStart Date: %s\nEnd Date: %s\nOverall Score: %s", course.getCourseId(), course.getDescription(),
                    course.getInstructor(), course.getStartDate(), course.getEndDate(), grade_temp));
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>
                (instance, android.R.layout.simple_list_item_1, rows);
        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener((parent, view, i, l) -> {
            // get item from ListView
            String selected_item = (String) parent.getItemAtPosition(i);
            // get course
            String course_Id = selected_item.split(" ")[1];
            course = dao.getCourseByCourseId(course_Id);

            if(course != null) {
                Intent intent = new Intent(DisplayUserCourseActivity.this, DisplayCourseActivity.class);
                intent.putExtra("course", course.getCourseId());
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            } else {
                alert("Error", "NullException");
                finish();
            }
        });

        lv.setOnItemLongClickListener((parent, view, i, l) -> {
            String selected_item = (String) parent.getItemAtPosition(i);
            String selected_temp = selected_item.split(" ")[1];
            Log.d("DisplayUserCourseActivity", selected_temp);
            Course course_temp = dao.getCourseByCourseId(selected_temp);
            editCourse(course_temp);

            return true;
        });

    }

    /**
     * Gets average of grade for all the assignments in a course
     *
     * @param assignments List of assignments from a course
     *
     * @return The average grade between all assignments
     */
    public double getAverage(List<Assignment> assignments) {
        double grades = 0;
        double earned_temp;
        double max_temp;
        for(Assignment assignment: assignments) {
            earned_temp = Integer.parseInt(String.valueOf(assignment.getEarnedScore()));
            max_temp = Integer.parseInt(String.valueOf(assignment.getMaxScore()));
            grades += (earned_temp / max_temp);
        }

        return (grades / assignments.size()) * 100;
    }

    public void editCourse(Course course) {
        if(course != null) {
            course_id_temp = course.getCourseId();

            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(55, 5, 15, 5);

            final EditText course_id = new EditText(this);
            course_id.setHint("Enter new Course ID");
            layout.addView(course_id);

            final EditText course_name = new EditText(this);
            course_name.setHint("Enter new Title");
            layout.addView(course_name);

            final EditText instructor_name = new EditText(this);
            instructor_name.setHint("Enter Instructor name");
            layout.addView(instructor_name);

            final EditText start_date = new EditText(this);
            start_date.setHint("Enter start date");
            start_date.setInputType(InputType.TYPE_CLASS_DATETIME);
            layout.addView(start_date);

            final EditText end_date = new EditText(this);
            end_date.setHint("Enter end date");
            end_date.setInputType(InputType.TYPE_CLASS_DATETIME);
            layout.addView(end_date);

            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("Edit Course")
                    .setPositiveButton("Okay", (dialog, which) -> {
                        String course_id_ = course_id.getText().toString();
                        String course_title_ = course_name.getText().toString();
                        String instructor_name_ = instructor_name.getText().toString();
                        String start_date_ = start_date.getText().toString();
                        String end_date_ = end_date.getText().toString();

                        if(course_id_.isEmpty() || course_title_.isEmpty() || instructor_name_.isEmpty() || start_date_.isEmpty() || end_date_.isEmpty()) {
                            alert("Error","Please don't leave any fields blank.");
                            return;
                        }

                        // check if course id is taken
                        CourseAppDAO dao = AppDatabase.getAppDatabase(this).getCourseDao();
                        Course course_check = dao.getCourseByCourseId(course_id_);
                        if(course_check == null) {
                            // id not taken
                            Log.d("DisplayUserCourseActivity", "course not taken");
                            course.setCourseId(course_id_);
                            course.setDescription(course_title_);
                            course.setInstructor(instructor_name_);
                            course.setStartDate(start_date_);
                            course.setEndDate(end_date_);

                            List<Assignment> assignments_temp = dao.getAllAssignmentsByCourseId(course_id_temp);
                            for(Assignment assignment: assignments_temp) {
                                assignment.setCourseId(course_id_);
                                dao.updateAssignment(assignment);
                            }
                            dao.updateCourse(course);
                            alert("Success!", "You have updated your course");

                        } else {
                            // course id taken
                            alert("Error", "Course ID is already taken, try another, or have you already added the class?");
                        }
                    })
                    .setNegativeButton("Cancel", (dialog, which) ->
                            alert("Edit Course", "No changes were made."))
                    .setOnCancelListener(dialog ->
                            Log.d("DisplayUserCourseActivity", "canceled, no changes"))
                    .setView(layout);
            AlertDialog alert = builder.create();
            alert.show();

        } else {
            // somehow null
            alert("Error", "Course does not exist");
            finish();
        }

    }
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

}