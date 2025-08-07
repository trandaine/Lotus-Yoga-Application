package com.tranquangdaine.applicationlotusyoga.infrastructure.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    // Define methods for interacting with the Category table here
    // For example:
     @Insert
     long insertCategory(Category category);

     @Update
     void updateCategory(Category category);

     @Delete
     void deleteCategory(Category category);

    // @Query("SELECT * FROM Category WHERE id = :categoryId")
    // Category getCategoryById(int categoryId);

     @Query("SELECT * FROM categories")
     List<Category> getAllCategories();

    @Query("SELECT * FROM categories WHERE categoryId = :id")
     Category getCategoryById(int id);
}
