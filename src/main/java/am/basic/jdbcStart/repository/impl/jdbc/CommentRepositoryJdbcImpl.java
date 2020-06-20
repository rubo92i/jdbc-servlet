package am.basic.jdbcStart.repository.impl.jdbc;

import am.basic.jdbcStart.model.Comment;
import am.basic.jdbcStart.repository.CommentRepository;
import am.basic.jdbcStart.util.DataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentRepositoryJdbcImpl implements CommentRepository {

    private DataSource dataSource;


    @Override
    public List<Comment> getByUserId(int userId) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement("SELECT * FROM comment WHERE user_id = ?");
        preparedStatement.setInt(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Comment comment = fromResultSet(resultSet);
            comments.add(comment);
        }

        return comments;
    }


    @Override
    public void save(Comment comment) throws SQLException {
        PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement("INSERT into comment(name,description,user_id) VALUES(?,?,?) ");
        preparedStatement.setString(1, comment.getName());
        preparedStatement.setString(2, comment.getDescription());
        preparedStatement.setInt(3, comment.getUserId());
        preparedStatement.execute();
    }


    @Override
    public void delete(int id) throws SQLException {
        PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement("DELETE FROM comment WHERE id = ?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();

    }



    private Comment fromResultSet(ResultSet resultSet) throws SQLException {
        Comment comment = new Comment();
        comment.setId(resultSet.getInt("id"));
        comment.setName(resultSet.getString("name"));
        comment.setDescription(resultSet.getString("description"));
        comment.setUserId(resultSet.getInt("user_id"));
        return comment;
    }


    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
