package com.example.projectonecourseapp.db;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = AppDatabase.USER_TABLE)
public class User
{

    @PrimaryKey(autoGenerate = true)
    private int mStudentId;

    private String mUserName;
    private String mPassword;
    private String mFirstName;
    private String mLastName;

    @Ignore
    public User() {}

    @Ignore
    public User(String username, String password) {
        this.mUserName = username;
        this.mPassword = password;
    }

    public User(String userName, String password, String firstName, String lastName) {
        this.mUserName = userName;
        this.mPassword = password;
        this.mFirstName = firstName;
        this.mLastName = lastName;
    }

    public int getStudentId() {
        return mStudentId;
    }

    public void setStudentId(int studentId) {
        mStudentId = studentId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    @Override
    public String toString() {
        return "\nUser{" +
                "UserName='" + mUserName + '\'' +
                '}';
    }
}
