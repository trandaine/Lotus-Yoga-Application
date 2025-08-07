package com.tranquangdaine.applicationlotusyoga.infrastructure.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Customer;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.UserTransaction;

public class CustomerWithTransactions {
    @Embedded
    public Customer customer;
    @Relation(
            parentColumn = "customerId",
            entityColumn = "customerId"
    )
    public UserTransaction userTransaction;
}
