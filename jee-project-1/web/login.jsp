<%@page import="java.sql.SQLException"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%
  if(request.getMethod().equals("POST")){
    String url = "jdbc:derby://localhost:1527/jee-project";
    String user = "login";
    String password = "password";
    Connection db;

    try{
      db = DriverManager.getConnection(url, user, password);

      Statement statement = db.createStatement();
      ResultSet data = statement.executeQuery("SELECT * FROM credentials WHERE login = '" 
              + request.getParameter("username").toString() +"' "
              + "AND mdp = '" + request.getParameter("password").toString() + "'");

      if (data.next()) {
        session.setAttribute("logged", true);
      }
      else{
        session.setAttribute("danger", "Login failed! Please check your login and/or password and try again");
      }
      
      db.close();
    }
    catch(SQLException e){
      out.println(e.getMessage());
    }
    finally{
      response.sendRedirect("index.jsp");
    }
  }
  else{
    request.getRequestDispatcher("index.jsp").forward(request, response);
  }
%>   
