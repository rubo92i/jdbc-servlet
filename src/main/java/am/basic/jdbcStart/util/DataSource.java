package am.basic.jdbcStart.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {


    private  Connection connection;

    /*  jdbc:{databaseType}://{databaseIp or hostname}:{database port}/{schemaName} */
    private static final String url = "jdbc:mysql://localhost:3306/test";

    private static final String username = "root";

    private static final String password = "";

    public  Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class clazz = Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(url, username, password);
            }
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
