package am.basic.jdbcStart.util;

import am.basic.jdbcStart.repository.UserRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {


    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
        UserRepository userRepository = (UserRepository) applicationContext.getBean("userRepositoryJpa");
        UserRepository userRepository1 = (UserRepository) applicationContext.getBean("userRepositoryJpa");

        System.out.println(userRepository);
        System.out.println(userRepository1);

    }
}
