package com.tranquangdaine.applicationlotusyoga.infrastructure.entities;

import androidx.room.Entity;

@Entity(tableName = ("customer_course_cross_ref"),
        primaryKeys = {"customerId", "courseId"})
public class CustomerCourseCrossRef {
    public int customerId;
    public int courseId;

    public CustomerCourseCrossRef(int customerId, int courseId) {
        this.customerId = customerId;
        this.courseId = courseId;
    }

    // Default constructor for Room
    public CustomerCourseCrossRef() {
        // No-arg constructor required by Room
    }
}
