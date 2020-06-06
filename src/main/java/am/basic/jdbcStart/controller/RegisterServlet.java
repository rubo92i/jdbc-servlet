package am.basic.jdbcStart.controller;

import am.basic.jdbcStart.model.User;
import am.basic.jdbcStart.model.exceptions.DuplicateDataException;
import am.basic.jdbcStart.model.exceptions.InternalServerException;
import am.basic.jdbcStart.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static am.basic.jdbcStart.util.constants.Messages.REGISTRATION_SUCCESS_MESSAGE;
import static am.basic.jdbcStart.util.constants.Pages.REGISTER_PAGE;
import static am.basic.jdbcStart.util.constants.Pages.VERIFICATION_PAGE;
import static am.basic.jdbcStart.util.constants.ParameterKeys.*;

public class RegisterServlet extends HttpServlet {

    private UserService userService = new UserService();

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter(NAME_PARAM_KEY);
        String surname = request.getParameter(SURNAME_PARAM_KEY);
        String username = request.getParameter(USERNAME_PARAM_KEY);
        String password = request.getParameter(PASSWORD_PARAM_KEY);


        try {

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setName(name);
            user.setSurname(surname);

            userService.register(user);
            request.setAttribute(USERNAME_PARAM_KEY, username);
            request.setAttribute(MESSAGE_ATTRIBUTE_KEY, REGISTRATION_SUCCESS_MESSAGE);
            request.getRequestDispatcher(VERIFICATION_PAGE).forward(request, response);

        } catch (DuplicateDataException | InternalServerException ex) {
            request.setAttribute(MESSAGE_ATTRIBUTE_KEY, ex.getMessage());
            request.getRequestDispatcher(REGISTER_PAGE).forward(request, response);
        }


    }

}
