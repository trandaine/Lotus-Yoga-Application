package com.tranquangdaine.applicationlotusyoga.infrastructure.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Category;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Course;

import java.util.List;

public class CategoryWithCourses {
    @Embedded
    public Category category;

    @Relation(
            parentColumn = "categoryId",
            entityColumn = "categoryId"
    )
    public List<Course> courses;
}
