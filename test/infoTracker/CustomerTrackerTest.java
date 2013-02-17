package infoTracker;

import database.DBConnection;
import database.DBTransaction;
import domain.Customer;
import exceptions.NicknameInvalidException;
import exceptions.ProperNameInvalidException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.sql.Connection;
import java.sql.Date;
import java.util.Calendar;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class CustomerTrackerTest {
    private static DBConnection dbConnection;
    private static Connection connection;
    private static CustomerInfoTracker tracker;
    private static DBTransaction transaction;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void connectWithMySQL() {
        dbConnection = new DBConnection();
        connection = dbConnection.getConnection();
        transaction = new DBTransaction(connection);
        tracker = new CustomerInfoTracker(transaction);
    }

    @AfterClass
    public static void disconnectWithMySQL() {
        tracker.deleteCustomer("poldi");
        tracker.deleteCustomer("nick");
        tracker.deleteCustomer("adam");
        tracker.deleteCustomer("gaolinsman");
        dbConnection.disconnect();
    }

    @Test
    public void shouldAddNewCustomerSuccessfullyWithoutInvalidCharacters() throws NicknameInvalidException, ProperNameInvalidException {
        String nickname = "poldi";
        Date dateOfBirth = new Date(Date.valueOf("1986-12-25").getTime());
        String properName = "Lucas Podolski";

        Customer customer = new Customer();
        customer.setNickname(nickname);
        customer.setDateOfBirth(dateOfBirth);
        customer.setProperName(properName);

        tracker.addCustomer(customer);
        assertThat(tracker.getCustomer(nickname), is(customer));
    }

    @Test
    public void shouldDeleteCustomerSuccessfully() throws NicknameInvalidException, ProperNameInvalidException {
        String nickname = "zhangmos";
        Date dateOfBirth = new Date(Date.valueOf("1989-12-25").getTime());
        String properName = "Zhang Linpeng";

        Customer customer = new Customer();
        customer.setNickname(nickname);
        customer.setDateOfBirth(dateOfBirth);
        customer.setProperName(properName);

        tracker.addCustomer(customer);
        assertThat(tracker.getCustomer(nickname), is(customer));

        tracker.deleteCustomer(nickname);

        assertNull(tracker.getCustomer(nickname).getNickname());
        assertNull(tracker.getCustomer(nickname).getDateOfBirth());
    }

    @Test
    public void shouldAddTwoCustomersWithSameProperNameSuccessfully() throws NicknameInvalidException, ProperNameInvalidException {
        String nickname1 = "nick";
        String properName1 = "Zhang Yue";

        Customer customer1 = new Customer();
        customer1.setNickname(nickname1);
        customer1.setProperName(properName1);

        String nickname2 = "adam";
        String properName3 = "Zhang Yue";

        Customer customer2 = new Customer();
        customer2.setNickname(nickname2);
        customer2.setProperName(properName3);

        tracker.addCustomer(customer1);
        tracker.addCustomer(customer2);

        assertEquals(tracker.getCustomer("nick").getProperName(), tracker.getCustomer("adam").getProperName());
    }

    @Test
    public void shouldAddTodayAsTheDateWhenUserJoinIn() throws NicknameInvalidException, ProperNameInvalidException {
        String nickname = "gaolinsman";
        Date dateOfBirth = new Date(Date.valueOf("1989-12-25").getTime());
        String properName = "Zhang Linpeng";

        Customer customer = new Customer();
        customer.setNickname(nickname);
        customer.setDateOfBirth(dateOfBirth);
        customer.setProperName(properName);

        tracker.addCustomer(customer);

        Calendar cal = Calendar.getInstance();
        java.util.Date utilDate = cal.getTime();
        java.sql.Date today = new Date(utilDate.getTime());

        assertThat(customer.getJoiningDate().toString(), is(today.toString()));
    }
}
