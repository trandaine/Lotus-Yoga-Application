package com.tranquangdaine.applicationlotusyoga.infrastructure.daos;

import androidx.room.Dao;
import androidx.room.Query;

import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.CustomerCourseCrossRef;

import java.util.List;

@Dao
public interface CustomerCourseCrossRefDao {
    @Query("SELECT * FROM customer_course_cross_ref")
    List<CustomerCourseCrossRef> getAllRefs();
}
