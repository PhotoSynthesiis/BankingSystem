package database;

import domain.Customer;
import exceptions.CustomerBalanceInvalid;
import exceptions.NicknameInvalidException;
import exceptions.ProperNameInvalidException;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Years;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBTransaction {
    public static Connection connection;
    private PreparedStatement pstmt;

    public DBTransaction() {
        this.connection = DBConnection.getConnection();
    }

    public void addCustomer(Customer customer) {
        String nickname = customer.getNickname();
        Date dateOfBirth = customer.getDateOfBirth();
        String properName = customer.getProperName();
        Date joiningDate = customer.getJoiningDate();
        double balance = customer.getBalance();

        String sql = "insert into userinfo values (?, ?, ?, ?, ?)";

        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, nickname);
            pstmt.setObject(2, dateOfBirth);
            pstmt.setString(3, properName);
            pstmt.setObject(4, joiningDate);
            pstmt.setDouble(5, balance);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Customer getCustomer(String nickname) {
        Customer customer = new Customer();
        String sql = "select * from userinfo where nickname = ?";
        ResultSet resultSet;

        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, nickname);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                customer.setNickname(resultSet.getString(1));
                customer.setDateOfBirth(resultSet.getDate(2));
                customer.setProperName(resultSet.getString(3));
                customer.setJoiningDate(resultSet.getDate(4));
                customer.setBalance(resultSet.getDouble(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NicknameInvalidException e) {
            e.printStackTrace();
        } catch (ProperNameInvalidException e) {
            e.printStackTrace();
        } catch (CustomerBalanceInvalid customerBalanceInvalid) {
            customerBalanceInvalid.printStackTrace();
        }
        return customer;
    }

    public void deleteCustomer(String nickname) {
        String sql = "delete from userinfo where nickname = ?";
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, nickname);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        DBConnection.disconnect();
    }

    public void addBonusToLoyalCustomer() {
        int bonus = 5;

        String currentDate = DateTime.now().toLocalDate().toString();
        Date today = new Date(Date.valueOf(currentDate).getTime());

        Date joiningDate;
        String nickname;
        double beforeBalance, afterBalance;
        String sql = "select * from userinfo";
        String sql2 = "update userinfo set balance = ? where nickname = ?";

        ResultSet resultSet;

        try {
            pstmt = connection.prepareStatement(sql);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                nickname = resultSet.getString(1);
                joiningDate = resultSet.getDate(4);
                beforeBalance = resultSet.getDouble(5);
                afterBalance = beforeBalance + bonus;

                if (Years.yearsBetween(new DateTime(joiningDate), new DateTime(today)).getYears() >= 2) {
                    System.out.println(nickname);
                    pstmt = connection.prepareStatement(sql2);
                    pstmt.setDouble(1, afterBalance);
                    pstmt.setString(2, nickname);
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getLoyalCustomers() {
        String currentDate = DateTime.now().toLocalDate().toString();
        Date today = new Date(Date.valueOf(currentDate).getTime());

        String nickname;
        Date joiningDate;
        String sql = "select * from userinfo";

        ResultSet resultSet;

        List<String> customers = new ArrayList<String>();

        try {
            pstmt = connection.prepareStatement(sql);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                nickname = resultSet.getString(1);
                joiningDate = resultSet.getDate(4);
                if (Years.yearsBetween(new DateTime(joiningDate), new DateTime(today)).getYears() >= 2) {
                    customers.add(nickname);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }
}
