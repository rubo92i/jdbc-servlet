package am.basic.jdbcStart.controller;

import am.basic.jdbcStart.model.User;
import am.basic.jdbcStart.model.exceptions.InvalidParametersException;
import am.basic.jdbcStart.model.exceptions.NotFoundException;
import am.basic.jdbcStart.model.exceptions.UnverifiedException;
import am.basic.jdbcStart.service.ServiceFactory;
import am.basic.jdbcStart.service.UserService;
import am.basic.jdbcStart.util.PasswordValidator;
import am.basic.jdbcStart.util.encoder.Encryptor;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

import static am.basic.jdbcStart.util.constants.Messages.*;
import static am.basic.jdbcStart.util.constants.Pages.*;
import static am.basic.jdbcStart.util.constants.ParameterKeys.*;

public class LoginServlet extends HttpServlet {


    private UserService userService = ServiceFactory.getUserService();


    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter(USERNAME_PARAM_KEY);
        String password = request.getParameter(PASSWORD_PARAM_KEY);
        String rememberMe = request.getParameter(REMEMBER_ME_PARAM_KEY);

        try {

            InvalidParametersException.check(username == null || username.length() < 5, USERNAME_INVALID_MESSAGE);
            InvalidParametersException.check(PasswordValidator.isInvalid(password), PASSWORD_INVALID_MESSAGE);

            User user = userService.login(username, password);
            HttpSession session = request.getSession();
            session.setAttribute(USER_ATTRIBUTE_KEY, user);

            if (rememberMe != null && rememberMe.equalsIgnoreCase("on")) {
                Cookie cookie = new Cookie(REMEMBER_TOKEN_COOKIE_KEY, Encryptor.encrypt(username + ":" + password));
                cookie.setMaxAge(360000);
                response.addCookie(cookie);
            }

            response.sendRedirect(HOME_PAGE);
        } catch (UnverifiedException e) {
            request.setAttribute(MESSAGE_ATTRIBUTE_KEY, e.getMessage());
            request.setAttribute(USERNAME_PARAM_KEY, username);
            request.getRequestDispatcher(VERIFICATION_PAGE).forward(request, response);
        } catch (NotFoundException   | InvalidParametersException e) {
            request.setAttribute(MESSAGE_ATTRIBUTE_KEY, e.getMessage());
            request.getRequestDispatcher(INDEX_PAGE).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute(MESSAGE_ATTRIBUTE_KEY, INTERNAL_ERROR_MESSAGE);
            request.getRequestDispatcher(INDEX_PAGE).forward(request, response);
        }

    }
}
