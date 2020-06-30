package am.basic.jdbcStart.repository.impl.spring.jdbc;

import am.basic.jdbcStart.model.User;
import am.basic.jdbcStart.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Data
@Repository  //default singleton name userRepositorySpringJdbcImpl
public class UserRepositorySpringJdbcImpl implements UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;




    @Override
    public void add(User user) {
        String query = "INSERT into user(code,name,password,surname,username,status) VALUES(?,?,?,?,?,?)";
        jdbcTemplate.update(query, user.getCode(), user.getName(), user.getPassword(), user.getSurname(), user.getUsername(), user.getStatus());
    }


    @Override
    public void update(User user) {
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public List<User> findByName(String name) {
        return null;
    }

    @Override
    public List<User> findByNameAndSurname(String name, String surname) {
        return null;
    }

    @Override
    public User getById(int id) {
        User user = jdbcTemplate.queryForObject("SELECT * FROM user WHERE id = " + id,new UserMapper());
        return user;
    }

    @Override
    public User getByUsername(String username) {
        User user = jdbcTemplate.queryForObject("SELECT * FROM  user  WHERE username = '" + username +"'", new UserMapper());
        return user;
    }


    @Override
    public void delete(User user) {

    }


    public static class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet sqlRowSet, int rowNum) throws SQLException {
            User user = new User();
            user.setId(sqlRowSet.getInt("id"));
            user.setName(sqlRowSet.getString("name"));
            user.setSurname(sqlRowSet.getString("surname"));
            user.setCode(sqlRowSet.getString("code"));
            user.setUsername(sqlRowSet.getString("username"));
            user.setPassword(sqlRowSet.getString("password"));
            user.setStatus(sqlRowSet.getInt("status"));
            return user;
        }
    }


}
