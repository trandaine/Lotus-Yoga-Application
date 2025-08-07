package com.tranquangdaine.applicationlotusyoga.infrastructure.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses",
        foreignKeys = {
            @ForeignKey(entity = Teacher.class, parentColumns = "teacherId", childColumns = "teacherId"),
            @ForeignKey(entity = Category.class, parentColumns = "categoryId", childColumns = "categoryId")
        })
public class Course {
    @PrimaryKey(autoGenerate = true)
    public int courseId;
    public String name;
    public String description;
    public String imageUrl;
    public int teacherId;        // Foreign key to teacher table
    public int categoryId;          // Foreign key to Category table
    public Integer duration; // in minutes
    public double price;
    public String level;
    public String room;

    public Course(String name, String description, String imageUrl, int teacherId, int categoryId, Integer duration, double price, String level, String room) {
        this.courseId = 0;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.teacherId = teacherId;
        this.categoryId = categoryId;
        this.duration = duration;
        this.price = price;
        this.level = level;
        this.room = room;
    }

    // Getters and Setters
    public int getId() {
        return courseId;
    }

    public void setId(int id) {
        this.courseId = id;
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

    public String getImageUrl() {
        return imageUrl;
    }
    public String getRoom() {return room;}
    public double getPrice() {
        return price;
    }
    public String getLevel() {
        return level;
    }
    public int getCategoryId() {
        return categoryId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    public void setRoom(String room) {
        this.room = room;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setLevel(String level) {
        this.level = level;
    }


    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public Course() {};
}
