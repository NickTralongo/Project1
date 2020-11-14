package com.example.projectonecourseapp;

import android.content.Context;
import android.content.Intent;

import com.example.projectonecourseapp.db.AppDatabase;
import com.example.projectonecourseapp.db.Assignment;
import com.example.projectonecourseapp.db.Course;
import com.example.projectonecourseapp.db.CourseAppDAO;
import com.example.projectonecourseapp.db.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Tests we use to test our functions and the DAO functions
 *
 */
@RunWith(RobolectricTestRunner.class)
public class ProjectOneCourseTests {

    private CourseAppDAO mCourseDAO;
    private AppDatabase tempDb;

    /**
     * Sets up a temporary database used to test DAO functions
     */
    @Before
    public void setUpDb() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        tempDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).allowMainThreadQueries().build();
        mCourseDAO = tempDb.getCourseDao();
    }

    /**
     * Ends the temporary database from further use
     */
    @After
    public void closeDb() {
        tempDb.close();
    }

    /**
     * Creates a user
     */
    @Test
    public void createUser() {
        User user = new User();
        assertNotNull(user);
    }

    /**
     * Sets the username for the user
     */
    @Test
    public void testUsername() {
        User user = new User();
        user.setUserName("din_djarin");
        assertEquals("din_djarin", user.getUserName());
    }

    /**
     * Sets the password for the user
     */
    @Test
    public void testPassword() {
        User user = new User();
        user.setPassword("baby_yoda_ftw");
        assertEquals("baby_yoda_ftw", user.getPassword());
    }

    /**
     * Tests basic add and delete functions of User table
     */
    @Test
    public void testingUserBasicInsertSearchDeleteDaoFunctions() {
        User user1 = new User("Bob", "Bob");

        mCourseDAO.addUser(user1);

        assertNotNull(mCourseDAO.getUserByUserAndPass("Bob", "Bob"));

        mCourseDAO.deleteUser(mCourseDAO.getUserByUserAndPass("Bob", "Bob"));

        assertNull(mCourseDAO.getUserByUserAndPass("Bob", "Bob"));
    }

    /**
     * Tests basic update function of User table
     */
    @Test
    public void testingUserDaoUpdateFunctions() {
        User user1 = new User("Bob", "Bob");

        mCourseDAO.addUser(user1);

        user1 = mCourseDAO.getUserByUserAndPass("Bob", "Bob");

        user1.setUserName("Totally Not Bob");

        mCourseDAO.updateUser(user1);

        assertNotNull(mCourseDAO.getUserByUserAndPass("Totally Not Bob", "Bob"));
    }

    /**
     * Tests the Course DAO functions that are used to get data from the COURSE table
     */
    @Test
    public void testingCourseDaoFunctions() {
        Course course1 = new Course("Soft", "CST-48", "Dr.K", "1/1/2001", "1/2/2001", "Bobby");

        mCourseDAO.addCourse(course1);

        assertNotNull(mCourseDAO.getCourseByCourseId("CST-48"));

        course1 = mCourseDAO.getCourseByCourseIdAndCourseName("CST-48", "Soft");

        course1.setCourseId("CST-Yes");

        mCourseDAO.updateCourse(course1);

        assertNotNull(mCourseDAO.getCourseByCourseId("CST-Yes"));

        mCourseDAO.deleteCourse(mCourseDAO.getCourseByCourseId("CST-Yes"));

        assertNull(mCourseDAO.getCourseByCourseId("CST-Yes"));
    }

    /**
     * Tests the Course DAO functions that are used to get data from the COURSE table
     */
    @Test
    public void testingAssignmentDaoFunctions() {
        Assignment assignment1 = new Assignment("Homework", "5", "2", "1/1/2001", "1/2/2001", "CST-48", "HW");

        mCourseDAO.addAssignment(assignment1);

        assertNotNull(mCourseDAO.getAllAssignmentsByCourseId("CST-48"));

        assignment1 = mCourseDAO.getAssignmentByDetails("Homework");

        assignment1.setDetails("Surprise Final Exam");

        mCourseDAO.updateAssignment(assignment1);

        assertNotNull(mCourseDAO.getAssignmentByDetails("Surprise Final Exam"));

        mCourseDAO.deleteAssignment(mCourseDAO.getAssignmentByDetails("Surprise Final Exam"));

        assertNull(mCourseDAO.getAssignmentByDetails("Surprise Final Exam"));
    }

    /**
     * Tests the Intent to see if the app could change activities to LoginActivity
     */
    @Test
    public void testLoginIntent() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        Intent i = testIntentFunction(context, "Bobert", LoginActivity.class);

        assertEquals("com.example.projectonecourseapp.LoginActivity", i.resolveActivity(context.getPackageManager()).getClassName());
    }

    /**
     * Tests the Intent to see if the app could change activities to EditUserActivity
     */
    @Test
    public void testEditUserIntent() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        Intent i = testIntentFunction(context, "Bobby", EditUserActivity.class);

        assertEquals("com.example.projectonecourseapp.EditUserActivity", i.resolveActivity(context.getPackageManager()).getClassName());
    }

    /**
     * Tests the Intent to see if the app could change activities to DisplayUserCourseActivity
     */
    @Test
    public void testDisplayUserCourseIntent() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        Intent i = testIntentFunction(context, "Bobbet", DisplayUserCourseActivity.class);

        assertEquals("com.example.projectonecourseapp.DisplayUserCourseActivity", i.resolveActivity(context.getPackageManager()).getClassName());
    }

    /**
     * Tests the Intent to see if the app could change activities to DisplayCourseActivity
     */
    @Test
    public void testDisplayCourseIntent() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        Intent i = testIntentFunction(context, "Bob", DisplayCourseActivity.class);

        assertEquals("com.example.projectonecourseapp.DisplayCourseActivity", i.resolveActivity(context.getPackageManager()).getClassName());
    }

    /**
     * Tests the Intent to see if the app could change activities to DeleteCourseActivity
     */
    @Test
    public void testDeleteCourseIntent() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        Intent i = testIntentFunction(context, "Bobby", DeleteCourseActivity.class);

        assertEquals("com.example.projectonecourseapp.DeleteCourseActivity", i.resolveActivity(context.getPackageManager()).getClassName());
    }

    /**
     * Tests the Intent to see if the app could change activities to CreateAccountActivity
     */
    @Test
    public void testCreateAccountIntent() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        Intent i = testIntentFunction(context, "Bobby", CreateAccountActivity.class);

        assertEquals("com.example.projectonecourseapp.CreateAccountActivity", i.resolveActivity(context.getPackageManager()).getClassName());
    }

    /**
     * Tests the Intent to see if the app could change activities to AddCourseActivity
     */
    @Test
    public void testAddCourseIntent() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        Intent i = testIntentFunction(context, "Bobby", AddCourseActivity.class);

        assertEquals("com.example.projectonecourseapp.AddCourseActivity", i.resolveActivity(context.getPackageManager()).getClassName());
    }

    /**
     * Tests the Intent to see if the app could change activities to AddAssignmentActivity
     */
    @Test
    public void testAddAssignmentIntent() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        Intent i = testIntentFunction(context, "Bobby", AddAssignmentActivity.class);

        assertEquals("com.example.projectonecourseapp.AddAssignmentActivity", i.resolveActivity(context.getPackageManager()).getClassName());
    }

    /**
     *
     * Function to help test the LoginActivity Intent
     *
     * @param context The activity we are starting from
     * @param username The username we pass in to test the Login Activity
     *
     * @return Intent of the new activity we are moving to
     */
    public static Intent testIntentFunction(Context context, String username, Class<?> activity) {
        Intent intent = new Intent(context, activity);
        intent.putExtra("Tag --- ", username);

        return intent;
    }

}