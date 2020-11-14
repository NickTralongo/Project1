package com.example.projectonecourseapp.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = AppDatabase.GRADE_CATEGORY_TABLE)
public class GradeCategory {

    @PrimaryKey
    private int mCategoryId;

    private String mTitle;
    private String mWeight;
    private int mGradeId;
    private String mAssignedDate;

    public GradeCategory(String title, String weight, String assignedDate) {
        this.mTitle = title;
        this.mWeight = weight;
        this.mAssignedDate = assignedDate;
    }

    public int getCategoryId() {
        return mCategoryId;
    }

    public void setCategoryId(int categoryId) {
        mCategoryId = categoryId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getWeight() {
        return mWeight;
    }

    public void setWeight(String weight) {
        mWeight = weight;
    }

    public int getGradeId() {
        return mGradeId;
    }

    public void setGradeId(int gradeId) {
        mGradeId = gradeId;
    }

    public String getAssignedDate() {
        return mAssignedDate;
    }

    public void setAssignedDate(String assignedDate) {
        mAssignedDate = assignedDate;
    }

    @Override
    public String toString() {
        return "GradeCategory{" +
                ", mTitle='" + mTitle + '\'' +
                ", mWeight='" + mWeight + '\'' +
                ", mGradeId='" + mGradeId + '\'' +
                ", mAssignedDate='" + mAssignedDate + '\'' +
                '}';
    }
}
