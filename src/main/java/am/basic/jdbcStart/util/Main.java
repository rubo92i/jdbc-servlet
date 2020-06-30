package am.basic.jdbcStart.util;

import am.basic.jdbcStart.model.exceptions.DuplicateDataException;
import am.basic.jdbcStart.model.exceptions.NotFoundException;
import am.basic.jdbcStart.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {


    public static void main(String[] args) throws DuplicateDataException, NotFoundException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
        UserService userService = applicationContext.getBean(UserService.class);

        userService.sendCode("surnameasda");
    }
}
