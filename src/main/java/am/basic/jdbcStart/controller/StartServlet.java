package am.basic.jdbcStart.controller;

import am.basic.jdbcStart.model.User;
import am.basic.jdbcStart.model.exceptions.NotFoundException;
import am.basic.jdbcStart.model.exceptions.UnverifiedException;
import am.basic.jdbcStart.service.ServiceFactory;
import am.basic.jdbcStart.service.UserService;
import am.basic.jdbcStart.util.CookieUtil;
import am.basic.jdbcStart.util.encoder.Encryptor;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

import static am.basic.jdbcStart.util.constants.Messages.INTERNAL_ERROR_MESSAGE;
import static am.basic.jdbcStart.util.constants.Pages.*;
import static am.basic.jdbcStart.util.constants.ParameterKeys.*;

public class StartServlet extends HttpServlet {


    private UserService userService = ServiceFactory.getUserService();


    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        Cookie rememberMeCookie = CookieUtil.getByName(request, REMEMBER_TOKEN_COOKIE_KEY);
        if (rememberMeCookie == null) {
            response.sendRedirect(INDEX_PAGE);
            return;
        }

        String token = null;
        try {
            token = Encryptor.decrypt(rememberMeCookie.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String username = token.split(":")[0];
        String password = token.split(":")[1];

        try {


            User user = userService.login(username, password);
            HttpSession session = request.getSession();
            session.setAttribute(USER_ATTRIBUTE_KEY, user);
            rememberMeCookie.setMaxAge(360000);
            response.sendRedirect(HOME_PAGE);


        } catch (UnverifiedException e) {
            request.setAttribute(MESSAGE_ATTRIBUTE_KEY, e.getMessage());
            request.setAttribute(USERNAME_PARAM_KEY, username);
            request.getRequestDispatcher(VERIFICATION_PAGE).forward(request, response);
        } catch (NotFoundException   e) {
            request.getRequestDispatcher(INDEX_PAGE).forward(request, response);
        } catch (RuntimeException exception) {
            request.setAttribute(MESSAGE_ATTRIBUTE_KEY, INTERNAL_ERROR_MESSAGE);
            request.getRequestDispatcher(INDEX_PAGE).forward(request, response);
        }

    }
}
