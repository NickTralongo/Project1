package com.example.projectonecourseapp.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = AppDatabase.ENROLLMENT_TABLE) //NOT USED
public class Enrollment
{

    @PrimaryKey
    private int mEnrollmentId;

    private int mStudentId;
    private int mCourseId;
    private String mEnrollmentDate;

    public Enrollment(String enrollmentDate) {
        this.mEnrollmentDate = enrollmentDate;
    }

    public int getEnrollmentId() {
        return mEnrollmentId;
    }

    public void setEnrollmentId(int enrollmentId) {
        mEnrollmentId = enrollmentId;
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

    public String getEnrollmentDate() {
        return mEnrollmentDate;
    }

    public void setEnrollmentDate(String enrollmentDate) {
        mEnrollmentDate = enrollmentDate;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "mStudentId='" + mStudentId + '\'' +
                ", mCourseId='" + mCourseId + '\'' +
                ", mEnrollmentDate='" + mEnrollmentDate + '\'' +
                '}';
    }
}
