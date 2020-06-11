package am.basic.jdbcStart.controller;

import am.basic.jdbcStart.model.Comment;
import am.basic.jdbcStart.model.User;
import am.basic.jdbcStart.model.exceptions.InternalServerException;
import am.basic.jdbcStart.model.exceptions.InvalidParametersException;
import am.basic.jdbcStart.service.CommentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static am.basic.jdbcStart.util.constants.Pages.HOME_PAGE;
import static am.basic.jdbcStart.util.constants.ParameterKeys.*;

public class AddCommentServlet extends HttpServlet {

    private CommentService commentService = new CommentService();

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter(NAME_PARAM_KEY);
        String description = request.getParameter(DESCRIPTION_PARAM_KEY);

        try {
            InvalidParametersException.check(name == null, "Please write something in name field");
            User user = (User) request.getSession().getAttribute(USER_ATTRIBUTE_KEY);

            Comment comment = new Comment();
            comment.setName(name);
            comment.setDescription(description);
            comment.setUserId(user.getId());

            commentService.add(comment);

            response.sendRedirect(HOME_PAGE);
        } catch (InvalidParametersException | InternalServerException e) {
            request.setAttribute(MESSAGE_ATTRIBUTE_KEY, e.getMessage());
            request.getRequestDispatcher(HOME_PAGE).forward(request, response);
        }

    }


}
