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
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ManagerTest {
    private static DBTransaction transaction;
    private static CustomerInfoTracker tracker;

    @BeforeClass
    public static void setUp() {
        transaction = new DBTransaction();
        tracker = new CustomerInfoTracker();
        tracker.setTransaction(transaction);
    }

    @AfterClass
    public static void tearDown() {
        tracker.deleteCustomer("ramos");
        tracker.deleteCustomer("zhangmos");
        tracker.deleteCustomer("ronaldo");

        transaction.disconnect();
    }

    @Test
    public void shouldGivenFiveEuroBonusGivenACustomerJoinMoreThanTwoYears() throws ProperNameInvalidException, NicknameInvalidException, CustomerBalanceInvalid {

        createUsers(tracker);
        Manager manager = new Manager();
        List<Double> initialBalance = new ArrayList<Double>();
        List<String> customerList = manager.getLoyalCustomers();

        double expectedDifference = 5.00;

        for (int i = 0; i < customerList.size(); i++) {
            initialBalance.add(tracker.getCustomer(customerList.get(i)).getBalance());
        }

        manager.addBonusToLoyalCustomer();

        for (int i = 0; i < customerList.size(); i++) {
            double actualBalanceDifference = tracker.getCustomer(customerList.get(i)).getBalance() - initialBalance.get(i);
            assertThat(actualBalanceDifference, is(expectedDifference));
        }
    }

    private void createUsers(CustomerInfoTracker tracker) throws NicknameInvalidException, ProperNameInvalidException, CustomerBalanceInvalid {
        String nickname = "zhangmos";
        Date dateOfBirth = new Date(Date.valueOf("1989-12-25").getTime());
        String properName = "Zhang Linpeng";
        double balance = 100.00;

        Customer customer = new Customer();
        customer.setNickname(nickname);
        customer.setDateOfBirth(dateOfBirth);
        customer.setProperName(properName);
        customer.setBalance(balance);

        tracker.addCustomer(customer);

        String nickname2 = "ramos";
        Date dateOfBirth2 = new Date(Date.valueOf("1989-12-25").getTime());
        String properName2 = "Zhang Linpeng";
        double balance2 = 200.00;

        Customer customer2 = new Customer();
        customer2.setNickname(nickname2);
        customer2.setDateOfBirth(dateOfBirth2);
        customer2.setProperName(properName2);
        customer2.setBalance(balance2);

        tracker.addCustomer(customer2);

        String nickname3 = "ronaldo";
        Date dateOfBirth3 = new Date(Date.valueOf("1989-12-25").getTime());
        String properName3 = "Zhang Linpeng";
        double balance3 = 300.00;

        Customer customer3 = new Customer();
        customer3.setNickname(nickname3);
        customer3.setDateOfBirth(dateOfBirth3);
        customer3.setProperName(properName3);
        customer3.setBalance(balance3);

        tracker.addCustomer(customer3);
    }
}
