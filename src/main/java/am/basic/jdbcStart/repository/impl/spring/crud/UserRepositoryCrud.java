package am.basic.jdbcStart.repository.impl.spring.crud;

import am.basic.jdbcStart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepositoryCrud extends JpaRepository<User,Integer> {

    User getByUsername(String username);

    @Query(nativeQuery = true,value = "SELECT * FROM user  WHERE username = :aaa")
    User getByUserN(@Param("aaa") String username);

    @Modifying
    void removeByCode(String code);



}
