package com.tranquangdaine.applicationlotusyoga.infrastructure.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "teachers")
public class Teacher {
    @PrimaryKey(autoGenerate = true)
    public int teacherId;
    public String name;
    public String bio;
    public String imageUrl;

    public Teacher(String name, String bio, String imageUrl) {
        this.teacherId = 0;
        this.name = name;
        this.bio = bio;
        this.imageUrl = imageUrl;
    }
    public Teacher() {};
    public int getId() {
        return teacherId;
    }

    public void setId(int id) {
        this.teacherId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
