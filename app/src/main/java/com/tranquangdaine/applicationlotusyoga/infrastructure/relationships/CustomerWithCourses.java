package com.tranquangdaine.applicationlotusyoga.infrastructure.relationships;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Course;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Customer;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.CustomerCourseCrossRef;

import java.util.List;

public class CustomerWithCourses {
    @Embedded
    public Customer customer;
    @Relation(
            parentColumn = "customerId",
            entityColumn = "customerId",
            associateBy = @Junction(
                    value = CustomerCourseCrossRef.class,
                    parentColumn = "customerId",
                    entityColumn = "courseId"
            )
    )
    public List<Course> courses;
}
