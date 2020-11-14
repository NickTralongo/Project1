package com.example.projectonecourseapp.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CourseAppDAO
{

    /**
     * Functions used for the Users table in the database
     */

    //User
    @Insert
    void addUser(User...users);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("select * from " + AppDatabase.USER_TABLE + " where mUserName = :username and mPassword = :password")
    User login(String username, String password);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE)
    List<User> getAllUsers();

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mUserName = :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mUserName = :username AND mPassword = :password")
    User getUserByUserAndPass(String username, String password);

    //********************************************

    /**
     * Functions used for the Course table in the database
     */

    //Course
    @Insert
    void addCourse(Course...courses);

    @Update
    void updateCourse(Course courses);

    @Delete
    void deleteCourse(Course course);

    @Query("select * from " + AppDatabase.COURSE_TABLE + " where mCourseId = :courseId")
    Course getCourseByCourseId(String courseId);

    @Query("select * from " + AppDatabase.COURSE_TABLE + " where mCourseId = :courseId AND mDescription = :courseName")
    Course getCourseByCourseIdAndCourseName(String courseId, String courseName);

    @Query("select * from " + AppDatabase.COURSE_TABLE + " where username = :username")
    List<Course> getCoursesTaken(String username);

    @Query("SELECT COUNT(*) FROM " + AppDatabase.COURSE_TABLE + " WHERE mDescription = :courseName")
    int countCourseName(String courseName);

    @Query("SELECT COUNT(*) FROM " + AppDatabase.COURSE_TABLE + " WHERE mCourseId = :courseId")
    int countCourseId(String courseId);

    //********************************************

    /**
     * Functions used for the Assignment table in the database
     */

    //Assignment
    @Insert
    void addAssignment(Assignment...assignments);

    @Update
    void updateAssignment(Assignment...assignments);

    @Delete
    void deleteAssignment(Assignment assignment);

    @Query("SELECT * FROM " + AppDatabase.ASSIGNMENT_TABLE + " order by mCategoryId desc ")
    List<Assignment> getAllAssignmentsByCategoryId();

    @Query("SELECT COUNT(*) FROM " + AppDatabase.ASSIGNMENT_TABLE + " WHERE mCourseId = :courseId")
    int countAssignmentsByCourseId(String courseId);

    @Query("SELECT * FROM " + AppDatabase.ASSIGNMENT_TABLE + " WHERE mCourseId = :courseId")
    List<Assignment> getAllAssignmentsByCourseId(String courseId);

    @Query("select * from " + AppDatabase.ASSIGNMENT_TABLE + " where mDetails = :details ")
    Assignment getAssignmentByDetails(String details);

    //********************************************

    //GradeCategory (Isn't used)

    //********************************************

    //Grade (Isn't Used)

    //********************************************

    //Enrollment (Isn't used)
}
