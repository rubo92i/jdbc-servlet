package am.basic.jdbcStart.service;

import am.basic.jdbcStart.model.Comment;
import am.basic.jdbcStart.model.exceptions.InternalServerException;
import am.basic.jdbcStart.repository.CommentRepository;
import am.basic.jdbcStart.util.DataSource;

import java.sql.SQLException;
import java.util.List;

import static am.basic.jdbcStart.util.constants.Messages.INTERNAL_ERROR_MESSAGE;

public class CommentService  {



    private CommentRepository commentRepository = new CommentRepository(new DataSource());



    public List<Comment> getByUserId(int userId) throws SQLException {
        return commentRepository.getByUserId(userId);
    }

    public void add(Comment comment) throws InternalServerException {
        try {
            commentRepository.save(comment);
        }catch (SQLException ex){
            ex.printStackTrace();
            throw new InternalServerException(INTERNAL_ERROR_MESSAGE);
        }
    }
}
