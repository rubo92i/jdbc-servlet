package am.basic.jdbcStart;

import am.basic.jdbcStart.model.User;
import am.basic.jdbcStart.repository.UserRepository;
import am.basic.jdbcStart.repository.impl.jpa.UserRepositoryJpaImpl;

import java.sql.SQLException;

public class Main {


    public static void main(String[] args) throws SQLException {
        UserRepository userRepository = new UserRepositoryJpaImpl();

        User user = userRepository.getById(27);
        user.setSurname("Manukyan");
        userRepository.update(user);
        System.out.println(user);
    }
}
