package com.tranquangdaine.applicationlotusyoga.infrastructure.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Customer;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.CustomerCourseCrossRef;

import java.util.List;

@Dao
public interface CustomerDao {
    // Define methods for database operations related to Customer entities
    // For example:
     @Insert
     void insertCustomer(Customer customer);

    // @Query("SELECT * FROM Customer WHERE Id = :id")
    // Customer getCustomerById(int id);

    // @Update
    // void updateCustomer(Customer customer);

    // @Delete
    // void deleteCustomer(Customer customer);

     @Query("SELECT * FROM customers")
     List<Customer> getAllCustomers();

}
