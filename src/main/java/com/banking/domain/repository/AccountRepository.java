package com.banking.domain.repository;

import com.banking.database.DBTransaction;
import com.banking.domain.entity.Customer;
import com.banking.exceptions.CustomerBalanceInvalid;

public class AccountRepository {
    private static DBTransaction transaction = new DBTransaction();

    public static void addBalance(double money, Customer customer) {
        transaction.addBalance(money, customer);
    }

    public static void withdrawBalance(double money, Customer customer) throws CustomerBalanceInvalid {
        transaction.withdrawBalance(money, customer);
    }
}
