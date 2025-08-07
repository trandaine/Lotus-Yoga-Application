package com.tranquangdaine.applicationlotusyoga.infrastructure.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Course;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Teacher;

import java.util.List;

public class TeacherWithCourses {
    @Embedded
    public Teacher teacher;

    @Relation(
            parentColumn = "teacherId",
            entityColumn = "teacherId"
    )
    public List<Course> courses;
}
