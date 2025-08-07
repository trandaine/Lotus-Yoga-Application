package com.tranquangdaine.applicationlotusyoga.infrastructure.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "customers")
public class Customer {
    @PrimaryKey(autoGenerate = true)
    public int customerId;
    public String name;
    public String email;
    public String password;
    public String dateCreated;
    public String phoneNumber;
    public String dateOfBirth;
    public String imageUrl;
    public double balance;

    public Customer(String name, String email, String phoneNumber, String password, String dateCreated, String dateOfBirth, String imageUrl, double balance) {
        this.customerId = 0;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.dateCreated = dateCreated;
        this.dateOfBirth = dateOfBirth;
        this.imageUrl = imageUrl;
        this.balance = balance;
    }

    public int getId() {
        return customerId;
    }

    public void setId(int id) {
        this.customerId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
