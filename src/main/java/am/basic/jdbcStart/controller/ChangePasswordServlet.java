package am.basic.jdbcStart.controller;

import am.basic.jdbcStart.model.User;
import am.basic.jdbcStart.model.exceptions.AccessDeniedException;
import am.basic.jdbcStart.model.exceptions.InvalidParametersException;
import am.basic.jdbcStart.model.exceptions.NotFoundException;
import am.basic.jdbcStart.model.exceptions.UnauthorizedException;
import am.basic.jdbcStart.service.ServiceFactory;
import am.basic.jdbcStart.service.UserService;
import am.basic.jdbcStart.util.PasswordValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static am.basic.jdbcStart.util.constants.Messages.*;
import static am.basic.jdbcStart.util.constants.Pages.HOME_PAGE;
import static am.basic.jdbcStart.util.constants.Pages.INDEX_PAGE;
import static am.basic.jdbcStart.util.constants.ParameterKeys.*;

public class ChangePasswordServlet extends HttpServlet {


    private UserService userService = ServiceFactory.getUserService();


    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String password = request.getParameter(PASSWORD_PARAM_KEY);
        String newPassword = request.getParameter(NEW_PASSWORD_PARAM_KEY);


        try {

            User sessionUser = (User) request.getSession().getAttribute(USER_ATTRIBUTE_KEY);
            UnauthorizedException.check(sessionUser == null, SESSION_EXPIRED_MESSAGE);

            InvalidParametersException.check(PasswordValidator.isInvalid(password), PASSWORD_INVALID_MESSAGE);
            InvalidParametersException.check(PasswordValidator.isInvalid(newPassword), PASSWORD_INVALID_MESSAGE);


            User user = userService.changePassword(sessionUser.getUsername(), password, newPassword);
            request.setAttribute(USER_ATTRIBUTE_KEY, user);
            request.setAttribute(MESSAGE_ATTRIBUTE_KEY, PASSWORD_CHANGE_SUCCESS_MESSAGE);
            request.getRequestDispatcher(HOME_PAGE).forward(request, response);


        } catch (UnauthorizedException e) {
            request.setAttribute(MESSAGE_ATTRIBUTE_KEY, e.getMessage());
            request.getRequestDispatcher(INDEX_PAGE).forward(request, response);
        } catch (NotFoundException | InvalidParametersException | AccessDeniedException e) {
            request.setAttribute(MESSAGE_ATTRIBUTE_KEY, e.getMessage());
            request.getRequestDispatcher(HOME_PAGE).forward(request, response);
        }

    }
}
