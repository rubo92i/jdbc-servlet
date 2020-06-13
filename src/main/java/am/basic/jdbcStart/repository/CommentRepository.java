package am.basic.jdbcStart.repository;

import am.basic.jdbcStart.model.Comment;

import java.sql.SQLException;
import java.util.List;

public interface CommentRepository {

    List<Comment> getByUserId(int userId) throws SQLException;

    void save(Comment comment) throws SQLException;

    void delete(int id) throws SQLException;

}
