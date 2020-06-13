package am.basic.jdbcStart.controller;

import am.basic.jdbcStart.model.exceptions.InvalidParametersException;
import am.basic.jdbcStart.model.exceptions.NotFoundException;
import am.basic.jdbcStart.service.ServiceFactory;
import am.basic.jdbcStart.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static am.basic.jdbcStart.util.constants.Messages.CODE_SUCCESSFULLY_SEND_MESSAGE;
import static am.basic.jdbcStart.util.constants.Messages.USERNAME_INVALID_MESSAGE;
import static am.basic.jdbcStart.util.constants.Pages.VERIFICATION_PAGE;
import static am.basic.jdbcStart.util.constants.ParameterKeys.MESSAGE_ATTRIBUTE_KEY;
import static am.basic.jdbcStart.util.constants.ParameterKeys.USERNAME_PARAM_KEY;

public class ResendCodeServlet extends HttpServlet {

    private UserService userService = ServiceFactory.getUserService();

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter(USERNAME_PARAM_KEY);

        try {

            InvalidParametersException.check(username == null || username.length() < 5, USERNAME_INVALID_MESSAGE);

            userService.sendCode(username);
            request.setAttribute(USERNAME_PARAM_KEY, username);
            request.setAttribute(MESSAGE_ATTRIBUTE_KEY, CODE_SUCCESSFULLY_SEND_MESSAGE);
            request.getRequestDispatcher(VERIFICATION_PAGE).forward(request, response);

        } catch (InvalidParametersException | NotFoundException  e) {
            request.setAttribute(USERNAME_PARAM_KEY, username);
            request.setAttribute(MESSAGE_ATTRIBUTE_KEY, e.getMessage());
            request.getRequestDispatcher(VERIFICATION_PAGE).forward(request, response);
        }

    }


}
