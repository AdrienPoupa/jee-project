<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="m1.jee.model.BeanMember"%>
<%
  if(request.getMethod().equals("POST")){
    String url = "jdbc:derby://localhost:1527/jee-project";
    String user = "login";
    String password = "password";
    Connection db;

    try{
      db = DriverManager.getConnection(url, user, password);
      
      BeanMember beanMember = new BeanMember();
      beanMember.setUsername(request.getParameter("username").toString());
      beanMember.setPassword(request.getParameter("password").toString());
      
      Statement statement = db.createStatement();
      ResultSet data = statement.executeQuery("SELECT * FROM credentials WHERE login = '" 
              + beanMember.getUsername() +"' "
              + "AND mdp = '" + beanMember.getPassword() + "'");

      if (data.next()) {
        session.setAttribute("logged", true);
      }
      else{
        session.setAttribute("danger", "Login failed! Please check your login and/or password and try again");
      }
      
      db.close();
    }
    catch(Exception e){
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
