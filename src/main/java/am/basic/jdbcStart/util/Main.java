package am.basic.jdbcStart.util;

import am.basic.jdbcStart.model.User;
import am.basic.jdbcStart.model.exceptions.DuplicateDataException;
import am.basic.jdbcStart.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {


    public static void main(String[] args) throws DuplicateDataException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
        UserService userService = applicationContext.getBean(UserService.class);
        User user = new User();
        user.setName("name");
        user.setUsername("surnameasda");
        user.setSurname("sdjifknlakmvdflkvn");
        user.setStatus(1);
        user.setPassword("dfsfsddfhsgxsfbgdfdsxc");

        userService.register(user);


    }
}
