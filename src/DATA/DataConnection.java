/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DATA;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class DataConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/qltv";
    private static final String Username = "pdt04";
    private static final String Password = "pdt04";

    public Connection getConnection() {
        Connection connection = null;
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, Username, Password);
            } catch (Exception e) {
                System.out.printf("\n Error Connection ! Can't connect to database");
                e.printStackTrace();
            }
        }
        return connection;
    }

    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                System.out.printf("\n Error ! Can't close this connect");
                e.printStackTrace();
            }
        }
    }
}
