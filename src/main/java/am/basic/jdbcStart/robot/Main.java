package am.basic.jdbcStart.robot;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {


    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
        Robot robot = applicationContext.getBean(Robot.class);
        robot.hit();
        robot.move();
        robot.speak();
    }
}
