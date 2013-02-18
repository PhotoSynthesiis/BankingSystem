package infoTracker;

import database.DBTransaction;
import domain.Customer;
import exceptions.CustomerBalanceInvalid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerAccountUpdater {
    private static Connection connection = DBTransaction.connection;
    private static PreparedStatement statement;
    private static ResultSet resultSet;

    public static void addBalance(double money, Customer customer) {
        String sql = "update userinfo set balance = ? where nickname = ?";

        try {
            statement = connection.prepareStatement(sql);
            statement.setDouble(1, money);
            statement.setString(2, customer.getNickname());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void withdrawBalance(double money2, Customer customer) throws CustomerBalanceInvalid {
        double initialBalance = 0, afterBalance;
        String sql1 = "select balance from userinfo where nickname = ?";
        String sql2 = "update userinfo set balance = ? where nickname = ?";
        String nickname = customer.getNickname();

        try {
            statement = connection.prepareStatement(sql1);
            statement.setString(1, nickname);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                initialBalance = resultSet.getDouble(1);
            }

            afterBalance = initialBalance - money2;

            if (afterBalance < 0) {
                throw new CustomerBalanceInvalid("YOU CAN NOT WITHDRAW MONEY MORE THAT YOU HAVE");
            }

            statement = connection.prepareStatement(sql2);
            statement.setDouble(1, afterBalance);
            statement.setString(2, nickname);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
