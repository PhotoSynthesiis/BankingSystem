package infoTracker;

import database.DBTransaction;
import domain.Customer;
import exceptions.CustomerBalanceInvalid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerAccountUpdater {
    private static DBTransaction transaction = new DBTransaction();

    public static void addBalance(double money, Customer customer) {
        transaction.addBalance(money, customer);
    }

    public static void withdrawBalance(double money, Customer customer) throws CustomerBalanceInvalid {
        transaction.withdrawBalance(money, customer);
    }
}
