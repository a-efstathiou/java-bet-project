package com.eap.plh24;

import java.sql.*;
import java.util.List;

public class StoreDataInMySQL {

    //This method stores the data of every customer in a database in the same format as the generated text file.
    public static void storeData() {

        //Database connection parameters
        final String url = "your_url";
        final String username = "your_username";
        final String password = "your_password";

        BetOrganization bo = BetOrganization.getInstance();
        List<Customer> customerList = bo.getCustomerList();


        // Create a database connection
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connection to the database was successful.");
            connection.setAutoCommit(false); // Disable auto-commit
            //All contents of the table gets deleted to start the simulation with no customers
            try (Statement deleteStatement = connection.createStatement()) {
                deleteStatement.executeUpdate("DELETE FROM customers");
            }

            //We use Prepared Statements to prevent SQL Injection Vulnerabilities
            String insertSQL = "INSERT INTO customers (customer_name, money_paid, gains) VALUES (?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                for (Customer customer : customerList) {
                    String customerName = customer.getName();
                    double moneyPaid = customer.getMoneyPlayed();
                    double gains = bo.getCustomerGains(customer);

                    preparedStatement.setString(1, customerName);
                    preparedStatement.setDouble(2, moneyPaid);
                    preparedStatement.setDouble(3, gains);
                    // We add the statement to a batch to execute all the statements at the same time instead
                    // of having a new update at each iteration of the for loop. This saves considerable time.
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
                connection.commit();
                System.out.println("The data were successfully uploaded to the database.");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
