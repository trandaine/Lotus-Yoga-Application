package com.tranquangdaine.applicationlotusyoga.infrastructure.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Teacher;

import java.util.List;

@Dao
public interface TeacherDao {
    // Define methods for database operations related to Teacher entities
    // For example:
     @Insert
     long insertTeacher(Teacher teacher);

     @Update
     void updateTeacher(Teacher teacher);
    //
     @Delete
     void deleteTeacher(Teacher teacher);
    //
     @Query("SELECT * FROM teachers")
     List<Teacher> getAllTeachers();
}
