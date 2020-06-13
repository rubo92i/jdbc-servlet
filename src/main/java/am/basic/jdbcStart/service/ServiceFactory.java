package am.basic.jdbcStart.service;

import am.basic.jdbcStart.repository.impl.jdbc.UserRepositoryJdbcImpl;
import am.basic.jdbcStart.repository.impl.jpa.UserRepositoryJpaImpl;
import am.basic.jdbcStart.util.DataSource;

public class ServiceFactory {

    private static boolean usJpa = true;


    public static UserService getUserService() {
        if (usJpa) {
            return new UserService(new UserRepositoryJpaImpl());
        } else {
            return new UserService(new UserRepositoryJdbcImpl(new DataSource()));
        }
    }
}
