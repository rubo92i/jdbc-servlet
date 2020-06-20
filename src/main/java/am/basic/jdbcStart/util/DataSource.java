package am.basic.jdbcStart.util;

import lombok.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Data
public class DataSource {


    private Connection connection;

    private String url;

    private String username;

    private String password;

    private String driverClassName;


    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class clazz = Class.forName(driverClassName);
                connection = DriverManager.getConnection(url, username, password);
            }
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
