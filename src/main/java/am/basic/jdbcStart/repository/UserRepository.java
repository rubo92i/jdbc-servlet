package am.basic.jdbcStart.repository;

import am.basic.jdbcStart.model.User;
import am.basic.jdbcStart.util.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private DataSource dataSource;

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void add(User user) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT into user VALUES(0,?,?,?,?,?,?)");
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getSurname());
        preparedStatement.setString(3, user.getUsername());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setString(5, user.getCode());
        preparedStatement.setInt(6, user.getStatus());
        int result = preparedStatement.executeUpdate();
    }

    public void update(User user) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE  user SET  name = ?, surname = ?,username = ?,password = ?,code = ?,status = ? WHERE id  = ?");
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getSurname());
        preparedStatement.setString(3, user.getUsername());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setString(5, user.getCode());
        preparedStatement.setInt(6, user.getStatus());
        preparedStatement.setInt(7, user.getId());
        preparedStatement.execute();
        preparedStatement.close();
    }

    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery("Select * FROM user ");
        while (resultSet.next()) {
            User user = fromResultSet(resultSet);
            users.add(user);
        }
        resultSet.close();
        return users;
    }

    public User getByNameAndSurname(String name, String surname) throws SQLException {
        User user = null;
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("Select * FROM user where name = ? and surname = ?");
        statement.setString(1, name);
        statement.setString(2, surname);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            user = fromResultSet(resultSet);
        }
        resultSet.close();
        statement.close();
        return user;
    }

    public List<User> findByName(String name) throws SQLException {
        List<User> users = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("Select * FROM user where name LIKE(CONCAT('%',?,'%'))");
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            User user = fromResultSet(resultSet);
            users.add(user);
        }
        resultSet.close();
        statement.close();
        return users;
    }

    public List<User> findByNameAndSurname(String name, String surname) throws SQLException {
        List<User> users = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("Select * FROM user where name LIKE(CONCAT('%',?,'%')) and surname LIKE(CONCAT('%',?,'%'))");
        statement.setString(1, name);
        statement.setString(2, surname);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            User user = fromResultSet(resultSet);
            users.add(user);
        }
        resultSet.close();
        statement.close();
        return users;
    }

    public User getById(int id) throws SQLException {
        User user = null;
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("Select * FROM user where id = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            user = fromResultSet(resultSet);
        }
        resultSet.close();
        statement.close();
        return user;
    }


    public User getByUsernameAndPassword(String username, String password) throws SQLException {
        User user = null;
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("Select * FROM user where username = ? and password = ?");
        statement.setString(1, username);
        statement.setString(2, password);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            user = fromResultSet(resultSet);
        }
        resultSet.close();
        statement.close();
        return user;
    }

    public User getByUsername(String username) throws SQLException {
        User user = null;
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("Select * FROM user where username = ? ");
        statement.setString(1, username);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            user = fromResultSet(resultSet);
        }
        resultSet.close();
        statement.close();
        return user;
    }

    public void delete(int id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM user WHERE student_id = ?");
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
        preparedStatement.close();
    }

    private User fromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setCode(resultSet.getString("code"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setStatus(resultSet.getInt("status"));
        return user;
    }


}
