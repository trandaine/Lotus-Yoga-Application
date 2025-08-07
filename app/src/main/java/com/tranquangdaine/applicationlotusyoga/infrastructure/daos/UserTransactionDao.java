package com.tranquangdaine.applicationlotusyoga.infrastructure.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.UserTransaction;

import java.util.List;

@Dao
public interface UserTransactionDao {
    // Define methods for inserting, updating, deleting, and querying user transactions
    // For example:
     @Insert
     void insertUserTransaction(UserTransaction userTransaction);

     @Update
     void updateUserTransaction(UserTransaction userTransaction);

    // @Delete
    // void deleteUserTransaction(UserTransaction userTransaction);

     @Query("SELECT * FROM user_transactions")
     List<UserTransaction> getAllUserTransactions();

    // Add more methods as needed for your application
}
