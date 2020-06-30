package am.basic.jdbcStart.repository.impl.jpa;

import am.basic.jdbcStart.model.User;
import am.basic.jdbcStart.repository.UserRepository;
import am.basic.jdbcStart.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UserRepositoryJpaImpl implements UserRepository {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void onCreate(){
        System.out.println("creating user repository jpa");
    }

    @Override
    public void add(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();

    }

    @Override
    public void update(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();

    }

    @Override
    public List<User> getAll() {
        Session session = sessionFactory.openSession();
        NativeQuery<User> nativeQuery = session.createNativeQuery("SELECT * FROM user", User.class);
        List<User> users = nativeQuery.getResultList();
        return users;

    }


    @Override
    public List<User> findByName(String name) {
        String query = "SELECT * FROM user WHERE name LIKE(CONCAT('%',:nameParameter,'%'))";
        Session session = sessionFactory.openSession();
        NativeQuery<User> nativeQuery = session.createNativeQuery(query, User.class);
        nativeQuery.setParameter("nameParameter", name);
        List<User> users = nativeQuery.getResultList();
        return users;

    }

    @Override
    public List<User> findByNameAndSurname(String name, String surname) {
        String query = "SELECT * FROM user WHERE name LIKE(CONCAT('%',:nameParameter,'%')) AND  surname LIKE(CONCAT('%',:surnameParam,'%'))";
        Session session = sessionFactory.openSession();
        NativeQuery<User> nativeQuery = session.createNativeQuery(query, User.class);
        nativeQuery.setParameter("nameParameter", name);
        nativeQuery.setParameter("surnameParam", surname);
        List<User> users = nativeQuery.getResultList();
        return users;

    }

    @Override
    public User getById(int id) {
        Session session = sessionFactory.openSession();
        User user = session.find(User.class, id);
        session.close();
        return user;

    }

    @Override
    public User getByUsername(String username) {
        String query = "SELECT * FROM user WHERE username = :username  ";
        Session session = sessionFactory.openSession();
        NativeQuery<User> nativeQuery = session.createNativeQuery(query, User.class);
        nativeQuery.setParameter("username", username);
        User user = nativeQuery.uniqueResult();
        return user;

    }

    public User getByUsernameCriteria(String username) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("username"), username));
        Query<User> query = session.createQuery(criteriaQuery);
        User user = query.uniqueResult();
        return user;
    }

    @Override
    public void delete(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(user);
        session.getTransaction().commit();
    }
}
