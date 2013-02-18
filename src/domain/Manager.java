package domain;

import database.DBTransaction;
import infoTracker.CustomerInfoTracker;

import java.util.List;

public class Manager {
    private DBTransaction transaction;
    private CustomerInfoTracker tracker;

    public Manager() {
        transaction = new DBTransaction();
        tracker = new CustomerInfoTracker();
    }


    public List<String> getLoyalCustomers() {
        tracker.setTransaction(transaction);
        List<String> customerList = tracker.getLoyalCustomers();
        return customerList;
    }

    public void addBonusToLoyalCustomer() {
        tracker.addBonusToLoyalCustomer();
    }
}
