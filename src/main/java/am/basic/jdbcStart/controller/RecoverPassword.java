package am.basic.jdbcStart.controller;

import am.basic.jdbcStart.model.exceptions.AccessDeniedException;
import am.basic.jdbcStart.model.exceptions.InvalidParametersException;
import am.basic.jdbcStart.model.exceptions.NotFoundException;
import am.basic.jdbcStart.service.ServiceFactory;
import am.basic.jdbcStart.service.UserService;
import am.basic.jdbcStart.util.PasswordValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static am.basic.jdbcStart.util.constants.Messages.*;
import static am.basic.jdbcStart.util.constants.Pages.INDEX_PAGE;
import static am.basic.jdbcStart.util.constants.Pages.RECOVER_PASSWORD_PAGE;
import static am.basic.jdbcStart.util.constants.ParameterKeys.*;

public class RecoverPassword extends HttpServlet {

    private UserService userService = ServiceFactory.getUserService();

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter(USERNAME_PARAM_KEY);
        String password = request.getParameter(PASSWORD_PARAM_KEY);
        String code = request.getParameter(CODE_PARAM_KEY);

        try {


            InvalidParametersException.check(code == null || code.length() != 5, CODE_INVALID_MESSAGE);
            InvalidParametersException.check(username == null || username.length() < 5, USERNAME_INVALID_MESSAGE);
            InvalidParametersException.check(PasswordValidator.isInvalid(password), PASSWORD_INVALID_MESSAGE);


            userService.recoverPassword(username, code, password);

            request.setAttribute(MESSAGE_ATTRIBUTE_KEY, PASSWORD_CHANGE_SUCCESS_MESSAGE);
            request.getRequestDispatcher(INDEX_PAGE).forward(request, response);

        } catch (InvalidParametersException | NotFoundException | AccessDeniedException e) {
            request.setAttribute(USERNAME_PARAM_KEY, username);
            request.setAttribute(MESSAGE_ATTRIBUTE_KEY, e.getMessage());
            request.getRequestDispatcher(RECOVER_PASSWORD_PAGE).forward(request, response);
        }

    }
}
