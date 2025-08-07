package com.tranquangdaine.applicationlotusyoga.infrastructure;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.tranquangdaine.applicationlotusyoga.infrastructure.daos.CategoryDao;
import com.tranquangdaine.applicationlotusyoga.infrastructure.daos.CourseDao;
import com.tranquangdaine.applicationlotusyoga.infrastructure.daos.CustomerCourseCrossRefDao;
import com.tranquangdaine.applicationlotusyoga.infrastructure.daos.CustomerDao;
import com.tranquangdaine.applicationlotusyoga.infrastructure.daos.TeacherDao;
import com.tranquangdaine.applicationlotusyoga.infrastructure.daos.UserTransactionDao;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Category;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Course;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Customer;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.CustomerCourseCrossRef;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Teacher;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.UserTransaction;

@Database(
        entities = {
                Customer.class,
                Course.class,
                CustomerCourseCrossRef.class,
                Teacher.class,
                UserTransaction.class,
                Category.class
        },
        version = 2,
        exportSchema = true
)

public abstract class LotusYogaDbContext extends RoomDatabase {
    public abstract CustomerDao customerDao();
    public abstract CourseDao courseDao();
    public abstract CustomerCourseCrossRefDao customerCourseCrossRefDao();
    public abstract TeacherDao teacherDao();
    public abstract UserTransactionDao userTransactionDao();
    public abstract CategoryDao categoryDao();

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // 1. Add new column
            database.execSQL("ALTER TABLE courses ADD COLUMN teacherId_temp INTEGER DEFAULT 0");

            // 2. Copy data (if teacherId was a string representing an int, cast it)
            // If not, you may need to manually map values before migration

            // 3. Create new table with correct schema
            database.execSQL(
                    "CREATE TABLE courses_new (" +
                            "courseId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                            "name TEXT, " +
                            "description TEXT, " +
                            "imageUrl TEXT, " +
                            "teacherId INTEGER NOT NULL, " +
                            "categoryId INTEGER NOT NULL, " +
                            "duration INTEGER, " +
                            "price REAL, " +
                            "level TEXT, " +
                            "room TEXT)"
            );

            // 5. Drop old table and rename new table
            database.execSQL("DROP TABLE courses");
            database.execSQL("ALTER TABLE courses_new RENAME TO courses");
        }
    };

    private static volatile LotusYogaDbContext instance;
    public static LotusYogaDbContext getInstance(Context context) {
        if (instance == null) {
            synchronized (LotusYogaDbContext.class) {
                if (instance == null) {
                    try {
                        instance = Room.databaseBuilder(
                                context.getApplicationContext(),
                                LotusYogaDbContext.class,
                                "lotus_yoga_database"
                        )
                                .addMigrations(MIGRATION_1_2)
                                .build();
                    } catch (Exception e) {
                        android.util.Log.e("LotusYogaDbContext", "Database creation failed: " + e.getMessage());
                        throw new RuntimeException("Failed to create database instance", e);
                    }
                }
            }
        }
        return instance;
    }
}
