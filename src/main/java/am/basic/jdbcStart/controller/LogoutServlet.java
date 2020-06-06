package am.basic.jdbcStart.controller;

import am.basic.jdbcStart.util.CookieUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static am.basic.jdbcStart.util.constants.Pages.INDEX_PAGE;
import static am.basic.jdbcStart.util.constants.ParameterKeys.REMEMBER_TOKEN_COOKIE_KEY;

public class LogoutServlet extends HttpServlet {


    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        CookieUtil.removeCookie(request, response, REMEMBER_TOKEN_COOKIE_KEY);
        response.sendRedirect(INDEX_PAGE);
    }


}
