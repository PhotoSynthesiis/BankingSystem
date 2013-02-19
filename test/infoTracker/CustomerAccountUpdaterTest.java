package infoTracker;

import database.DBTransaction;
import domain.Customer;
import exceptions.CustomerBalanceInvalid;
import exceptions.NicknameInvalidException;
import exceptions.ProperNameInvalidException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.sql.Date;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CustomerAccountUpdaterTest {
    private static CustomerInfoTracker tracker;
    private static DBTransaction transaction;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void connectWithMySQL() {
        transaction = new DBTransaction();
        tracker = new CustomerInfoTracker();
        tracker.setTransaction(transaction);
    }

    @AfterClass
    public static void disconnectWithMySQL() {
        tracker.deleteCustomer("robin");
        tracker.deleteCustomer("shrek");
        tracker.deleteCustomer("bean");
        tracker.deleteCustomer("kiss");

        transaction.disconnect();
    }

    @Test
    public void shouldAddBalanceSuccessfully() throws NicknameInvalidException, ProperNameInvalidException, CustomerBalanceInvalid {
        String nickname = "robin";
        Date dateOfBirth = new Date(Date.valueOf("1983-7-22").getTime());
        String properName = "Robin Van Persie";
        double money = 100.00;

        Customer customer = new Customer();
        customer.setNickname(nickname);
        customer.setDateOfBirth(dateOfBirth);
        customer.setProperName(properName);
        customer.setBalance(money);

        tracker.addCustomer(customer);

        double money2 = 101.00;
        customer.deposit(money2);

        double expectedBalance = 201.00;

        assertThat(tracker.getCustomer(nickname).getBalance(), is(expectedBalance));
    }

    @Test
    public void shouldWithdrawSuccessfully() throws NicknameInvalidException, ProperNameInvalidException, CustomerBalanceInvalid {
        String nickname = "shrek";
        Date dateOfBirth = new Date(Date.valueOf("1986-7-22").getTime());
        String properName = "Wayne Rooney";
        double money = 100.00;

        Customer customer = new Customer();
        customer.setNickname(nickname);
        customer.setDateOfBirth(dateOfBirth);
        customer.setProperName(properName);
        customer.setBalance(money);

        tracker.addCustomer(customer);

        double money2 = 10;
        customer.withdraw(money2);

        double expectedBalance = 90.00;
        assertThat(tracker.getCustomer(nickname).getBalance(), is(expectedBalance));
    }

    @Test
    public void shouldThrowExceptionWhenTryToWithdrawExtraMoney() throws NicknameInvalidException, ProperNameInvalidException, CustomerBalanceInvalid {
        expectedException.expect(CustomerBalanceInvalid.class);

        String nickname = "bean";
        Date dateOfBirth = new Date(Date.valueOf("1986-7-22").getTime());
        String properName = "Chicarito";
        double money = 100.00;

        Customer customer = new Customer();
        customer.setNickname(nickname);
        customer.setDateOfBirth(dateOfBirth);
        customer.setProperName(properName);
        customer.setBalance(money);

        tracker.addCustomer(customer);

        double money2 = 110;
        customer.withdraw(money2);
    }

    @Test
    public void shouldAllowUserWithdrawAllMoney() throws NicknameInvalidException, ProperNameInvalidException, CustomerBalanceInvalid {
        String nickname = "kiss";
        Date dateOfBirth = new Date(Date.valueOf("1985-07-02").getTime());
        String properName = "Laurent Koscieny";
        double money = 100.00;

        Customer customer = new Customer();
        customer.setNickname(nickname);
        customer.setDateOfBirth(dateOfBirth);
        customer.setProperName(properName);
        customer.setBalance(money);

        tracker.addCustomer(customer);

        double money2 = 100;
        customer.withdraw(money2);

        double expectedBalance = 0;
        assertThat(tracker.getCustomer(nickname).getBalance(), is(expectedBalance));
    }
}
