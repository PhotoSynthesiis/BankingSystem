package database;

import domain.Customer;
import exceptions.NicknameInvalidException;
import exceptions.ProperNameInvalidException;

import java.sql.*;

public class DBTransaction {
    private Connection connection;
    private PreparedStatement pstmt;


    public DBTransaction(Connection connection) {
        this.connection = connection;
    }

    public void addCustomer(Customer customer) {
        String nickname = customer.getNickname();
        Date dateOfBirth = customer.getDateOfBirth();
        String properName = customer.getProperName();
        Date joiningDate = customer.getJoiningDate();

        String sql = "insert into userinfo values (?, ?, ?, ?)";

        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, nickname);
            pstmt.setObject(2, dateOfBirth);
            pstmt.setString(3, properName);
            pstmt.setObject(4, joiningDate);

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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NicknameInvalidException e) {
            e.printStackTrace();
        } catch (ProperNameInvalidException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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
}
