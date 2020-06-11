package am.basic.jdbcStart.controller;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static am.basic.jdbcStart.util.constants.Pages.INDEX_PAGE;


public class CookeTestServlet extends HttpServlet {


    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName() + " : " + cookie.getValue());
            }
        }



        Cookie cookie = new Cookie("test","fsdgsdgdfgdfgdfgdf");
        cookie.setMaxAge(10);
        response.addCookie(cookie);
        response.sendRedirect(INDEX_PAGE);

    }


}
