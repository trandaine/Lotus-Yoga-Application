package com.tranquangdaine.applicationlotusyoga.infrastructure.relationships;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Course;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Customer;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.CustomerCourseCrossRef;

import java.util.List;

public class CourseWithCustomers {
    @Embedded
    public Course course;

    @Relation(
            parentColumn = "courseId",
            entityColumn = "courseId",
            associateBy = @Junction(
                    value = CustomerCourseCrossRef.class,
                    parentColumn = "courseId",
                    entityColumn = "customerId")
            )
    public List<Customer> customers;

}
