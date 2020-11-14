package com.example.projectonecourseapp.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = AppDatabase.GRADE_TABLE) //NOT USED
public class Grade {

    @PrimaryKey
    private int mGradeId;

    private String mScore;
    private int mAssignmentId;
    private int mStudentId;
    private int mCourseId;
    private String mDateEarned;

    public Grade(String score, String dateEarned) {
        this.mScore = score;
        this.mDateEarned = dateEarned;
    }

    public int getGradeId() {
        return mGradeId;
    }

    public void setGradeId(int gradeId) {
        mGradeId = gradeId;
    }

    public String getScore() {
        return mScore;
    }

    public void setScore(String score) {
        mScore = score;
    }

    public int getAssignmentId() {
        return mAssignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        mAssignmentId = assignmentId;
    }

    public int getStudentId() {
        return mStudentId;
    }

    public void setStudentId(int studentId) {
        mStudentId = studentId;
    }

    public int getCourseId() {
        return mCourseId;
    }

    public void setCourseId(int courseId) {
        mCourseId = courseId;
    }

    public String getDateEarned() {
        return mDateEarned;
    }

    public void setDateEarned(String dateEarned) {
        mDateEarned = dateEarned;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "mScore='" + mScore + '\'' +
                ", mAssignmentId='" + mAssignmentId + '\'' +
                ", mStudentId='" + mStudentId + '\'' +
                ", mCourseId='" + mCourseId + '\'' +
                ", mDateEarned='" + mDateEarned + '\'' +
                '}';
    }
}
