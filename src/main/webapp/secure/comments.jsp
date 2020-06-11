<%@ page import="am.basic.jdbcStart.model.Comment" %>
<%@ page import="am.basic.jdbcStart.model.User" %>
<%@ page import="static am.basic.jdbcStart.util.constants.ParameterKeys.USER_ATTRIBUTE_KEY" %>
<%@ page import="am.basic.jdbcStart.service.CommentService" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: ruben.manukyan
  Date: 6/11/2020
  Time: 20:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>


<%
    CommentService commentService = new CommentService();
    User user = (User) session.getAttribute(USER_ATTRIBUTE_KEY);
    List<Comment> comments = commentService.getByUserId(user.getId());
%>

<table border="solid 1px">


    <%
        for (Comment comment : comments) {
    %>
    <tr>
        <td><%=comment.getName()%>
        </td>
        <td><%=comment.getDescription()%>
        </td>
        <td>
            <form method="post" action="/secure/delete-comment">
                <input type="hidden" name="id" value="<%=comment.getId()%>">
                <input type="submit" value="DELETE">
            </form>
        </td>
    <%
        }
    %>

</table>

<form method="post" action="/secure/add-comment">
    Comment : <input type="text" name="name">
    Description : <input type="text" name="description">
    <input type="submit" value="add">
</form>


</body>
</html>
