package am.basic.jdbcStart.controller;

import am.basic.jdbcStart.model.User;
import am.basic.jdbcStart.model.exceptions.DuplicateDataException;
import am.basic.jdbcStart.model.exceptions.InvalidParametersException;
import am.basic.jdbcStart.service.ServiceFactory;
import am.basic.jdbcStart.service.UserService;
import am.basic.jdbcStart.util.PasswordValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static am.basic.jdbcStart.util.constants.Messages.*;
import static am.basic.jdbcStart.util.constants.Pages.REGISTER_PAGE;
import static am.basic.jdbcStart.util.constants.Pages.VERIFICATION_PAGE;
import static am.basic.jdbcStart.util.constants.ParameterKeys.*;

public class RegisterServlet extends HttpServlet {

    private UserService userService = ServiceFactory.getUserService();

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter(NAME_PARAM_KEY);
        String surname = request.getParameter(SURNAME_PARAM_KEY);
        String username = request.getParameter(USERNAME_PARAM_KEY);
        String password = request.getParameter(PASSWORD_PARAM_KEY);


        try {

            InvalidParametersException.check(username == null || username.length() < 5, USERNAME_INVALID_MESSAGE);
            InvalidParametersException.check(PasswordValidator.isInvalid(password), PASSWORD_INVALID_MESSAGE);

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setName(name);
            user.setSurname(surname);

            userService.register(user);
            request.setAttribute(USERNAME_PARAM_KEY, username);
            request.setAttribute(MESSAGE_ATTRIBUTE_KEY, REGISTRATION_SUCCESS_MESSAGE);
            request.getRequestDispatcher(VERIFICATION_PAGE).forward(request, response);

        } catch (DuplicateDataException | InvalidParametersException ex) {
            request.setAttribute(MESSAGE_ATTRIBUTE_KEY, ex.getMessage());
            request.getRequestDispatcher(REGISTER_PAGE).forward(request, response);
        }


    }

}
