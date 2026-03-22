<%-- Redirect root "/" to our HomeServlet at "/home" --%>
<% response.sendRedirect(request.getContextPath() + "/home"); %>
