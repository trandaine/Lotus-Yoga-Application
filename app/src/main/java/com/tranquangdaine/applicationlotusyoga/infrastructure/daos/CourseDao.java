package com.tranquangdaine.applicationlotusyoga.infrastructure.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Category;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Course;

import java.util.List;

@Dao
public interface CourseDao {
    // Define methods for database operations related to Course entities
    // For example:
    @Insert
    void insertCourse(Course course);

    @Query("SELECT * FROM courses")
    List<Course> getAllCourses();

    @Update
    void updateCourse(Course course);

    @Delete
    void deleteCourse(Course course);

    @Query("SELECT * FROM courses WHERE courseId = :id")
    Course getCourseById(int id);

    // Additional methods can be defined as needed
}
