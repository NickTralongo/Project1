package com.example.projectonecourseapp.db;

import android.content.Context;
import android.util.Log;

import com.example.projectonecourseapp.db.typeConverters.DateTypeConverter;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.List;

@Database(entities = {User.class, Grade.class, GradeCategory.class, Enrollment.class, Course.class, Assignment.class}, version = 5)
@TypeConverters(DateTypeConverter.class)
public abstract class AppDatabase extends RoomDatabase
{
    private static AppDatabase instance;

    public static final String DB_NAME = "COURSE_DATABASE";

    public static final String USER_TABLE = "USER_TABLE";
    public static final String COURSE_TABLE = "COURSE_TABLE";
    public static final String GRADE_CATEGORY_TABLE = "GRADE_CATEGORY_TABLE";
    public static final String ASSIGNMENT_TABLE = "ASSIGNMENT_TABLE";
    public static final String GRADE_TABLE = "GRADE_TABLE";
    public static final String ENROLLMENT_TABLE = "ENROLLMENT_TABLE";

    public abstract CourseAppDAO getCourseDao();

    public static AppDatabase getAppDatabase(final Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class,
                    DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public void loadData(Context context) {
        List<User> userList = AppDatabase.getAppDatabase(context).getCourseDao().getAllUsers();
        if(userList.size() == 0) {
            loadUsers(context);
        }
    }

    public void loadUsers(Context context) {
        CourseAppDAO dao = getAppDatabase(context).getCourseDao();

        User djarin = new User("din_djarin", "baby_yoda_ftw");
        dao.addUser(djarin);
        Log.d("AppDatabase", "Added djarin for text");
    }

}