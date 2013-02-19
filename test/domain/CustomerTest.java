package domain;

import database.DBTransaction;
import exceptions.CustomerBalanceInvalid;
import exceptions.NicknameInvalidException;
import exceptions.ProperNameInvalidException;
import infoTracker.CustomerInfoTracker;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Date;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CustomerTest {
    private static CustomerInfoTracker tracker;
    private static DBTransaction transaction;

    @BeforeClass
    public static void connectWithMySQL() {
        transaction = new DBTransaction();
        tracker = new CustomerInfoTracker();
        tracker.setTransaction(transaction);
    }

    @AfterClass
    public static void disconnectWithMySQL() {
        tracker.deleteCustomer("lights");
        transaction.disconnect();
    }

    @Test
    public void shouldAddBonusToLoyalCustomerWhenTheyAreDepositing() throws NicknameInvalidException, ProperNameInvalidException, CustomerBalanceInvalid {
        String nickname = "lights";
        Date dateOfBirth = new Date(Date.valueOf("1980-07-01").getTime());
        String properName = "Frank Lampard";
        double money = 100.00;

        Customer customer = new Customer();
        customer.setNickname(nickname);
        customer.setDateOfBirth(dateOfBirth);
        customer.setProperName(properName);
        customer.setBalance(money);
        customer.setIsLoyalCustomer(true);
        customer.setIsBonusAdded(false);

        tracker.addCustomer(customer);
        customer.deposit(50);

        double expectedBalance = 155;
        assertThat(tracker.getCustomer(nickname).getBalance(), is(expectedBalance));
    }

    @Test
    public void shouldAddBonusToLoyalCustomerOnce() throws NicknameInvalidException, ProperNameInvalidException, CustomerBalanceInvalid {
        String nickname = "creek";
        Date dateOfBirth = new Date(Date.valueOf("1981-07-01").getTime());
        String properName = "Rio Ferdinand";
        double money = 100.00;

        Customer customer = new Customer();
        customer.setNickname(nickname);
        customer.setDateOfBirth(dateOfBirth);
        customer.setProperName(properName);
        customer.setBalance(money);
        customer.setIsLoyalCustomer(true);
        customer.setIsBonusAdded(false);

        tracker.addCustomer(customer);
        customer.deposit(50);

        double expectedBalance = 155;
        assertThat(tracker.getCustomer(nickname).getBalance(), is(expectedBalance));

        customer.deposit(50);
        assertThat(tracker.getCustomer(nickname).getBalance(), is(expectedBalance));
    }
}
