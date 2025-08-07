package com.tranquangdaine.applicationlotusyoga.infrastructure.entities;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_transactions",
        foreignKeys = {
            @ForeignKey(entity = Customer.class,
                    parentColumns = "customerId",
                    childColumns = "customerId",
                    onDelete = androidx.room.ForeignKey.CASCADE),
            @ForeignKey(entity = Course.class,
                    parentColumns = "courseId",
                    childColumns = "courseId",
                    onDelete = androidx.room.ForeignKey.CASCADE)
        })
public class UserTransaction {
    @PrimaryKey(autoGenerate = true)
    public int transactionId;
    public int customerId;          // Foreign key to customer
    public int courseId;             // Foreign key to course
    public String transactionDate;
    public String status;
    public double amount;
    public String paymentMethod;

    public UserTransaction(int customerId, int courseId, String transactionDate, String status, double amount, String paymentMethod) {
        this.transactionId = 0; // Default value, will be set by the database
        this.customerId = customerId;
        this.courseId = courseId;
        this.transactionDate = transactionDate;
        this.status = status;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }
}
