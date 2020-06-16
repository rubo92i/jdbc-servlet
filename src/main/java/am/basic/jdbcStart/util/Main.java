package am.basic.jdbcStart.util;

import am.basic.jdbcStart.model.Student;
import org.hibernate.Session;

public class Main {


    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Student student = session.find(Student.class,13);
        System.out.println(student);
     }
}
