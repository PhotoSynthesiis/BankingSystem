package infoTracker;

import database.DBTransaction;
import domain.Customer;

import java.sql.Date;
import java.util.Calendar;

public class CustomerInfoTracker {
    private DBTransaction transaction;

    public CustomerInfoTracker(DBTransaction transaction) {
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
}
