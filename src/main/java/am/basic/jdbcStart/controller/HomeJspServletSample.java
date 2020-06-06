package am.basic.jdbcStart.controller;

import am.basic.jdbcStart.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomeJspServletSample extends HttpServlet {


    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.getWriter().println("<html>\n" +
                "<head>\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1 style=\"background: cornflowerblue\">This is home page</h1>\n");
        User user = (User) request.getAttribute("user");
        response.getWriter().write("Hello dear " + user.getName() + " " + user.getSurname());
        response.getWriter().write("<br><br>\n");
        if (request.getAttribute("message") != null) {
            response.getWriter().write(request.getAttribute("message").toString());
        }
        response.getWriter().write("<br><br>\n");

        response.getWriter().write("<form method=\"post\" action=\"/change-password\">\n" +
                "    <input type=\"hidden\" name=\"username\" value=\" " + user.getUsername() + "\"><br/>\n" +
                "    old password : <input type=\"text\" name=\"password\"><br>\n" +
                "    new password : <input type=\"text\" name=\"new_password\"><br>\n" +
                "\n" +
                "    <input type=\"submit\" name=\"submit\"><br>\n" +
                "</form>\n" +
                "\n" +
                "\n" +
                "</body>\n" +
                "</html>");


    }

}
