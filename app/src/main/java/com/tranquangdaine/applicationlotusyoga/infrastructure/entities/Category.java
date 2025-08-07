package com.tranquangdaine.applicationlotusyoga.infrastructure.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class Category {
    @PrimaryKey(autoGenerate = true)
    public int categoryId;
    public String name;
    public String description;

    public Category(String name, String description) {
        this.categoryId = 0;
        this.name = name;
        this.description = description;
    }
    public Category(){}

    public int getId() {
        return categoryId;
    }

    public void setId(int id) {
        this.categoryId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
