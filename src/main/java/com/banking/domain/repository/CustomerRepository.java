package com.banking.domain.repository;

import com.banking.database.DBTransaction;
import com.banking.domain.entity.Customer;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class CustomerRepository {
    private DBTransaction transaction;

    public CustomerRepository() {
    }

    public void setTransaction(DBTransaction transaction) {
        this.transaction = transaction;
    }

    public void addCustomer(Customer customer) {
        Calendar cal = Calendar.getInstance();
        java.util.Date utilDate = cal.getTime();
        java.sql.Date today = new Date(utilDate.getTime());

        customer.setJoiningDate(today);
        transaction.addCustomer(customer);
    }

    public Customer getCustomer(String nickname) {
        return transaction.getCustomer(nickname);
    }

    public void deleteCustomer(String nickname) {
        transaction.deleteCustomer(nickname);
    }

    public List<String> getLoyalCustomers() {
        return transaction.getLoyalCustomers();
    }

    public void addBonusToLoyalCustomer() {
        List<String> loyalCustomers = getLoyalCustomers();
        for (int i = 0; i < loyalCustomers.size(); i++) {
            if(!getCustomer(loyalCustomers.get(i)).isBonusAdded()) {
                transaction.addBonusToLoyalCustomer();
            }
        }
    }
}
