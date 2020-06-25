package am.basic.jdbcStart.repository.impl.spring.jpa;

import am.basic.jdbcStart.model.User;
import org.hibernate.Session;

public class UserRepositorySpringJpaImplProxy extends UserRepositorySpringJpaImpl {

    @Override
    public void add(User user) {
         Session session = getSessionFactory().openSession();
         try{
             session.beginTransaction();
             super.add(user);
             session.getTransaction().commit();
         }catch (RuntimeException exception){
             session.getTransaction().rollback();
         }finally {
             session.clear();
             session.close();
         }
    }

}
