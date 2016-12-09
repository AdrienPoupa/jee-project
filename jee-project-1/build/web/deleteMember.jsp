<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%
  if(session.getAttribute("logged") == null){
    response.sendRedirect("index.jsp");
  }
  
  Connection db;
 
  String url = "jdbc:derby://localhost:1527/jee-project";
  String user = "login";
  String password = "password";

  try{
    db = DriverManager.getConnection(url, user, password);
    
    String select[] = request.getParameterValues("member");

    if (select != null && select.length != 0) {
      for (int i = 0; i < select.length; i++) {
        PreparedStatement statement = db.prepareStatement("DELETE FROM members WHERE ID = ?");
        statement.setInt(1, Integer.parseInt(select[i].trim()));
        statement.execute();
      }
      
      session.setAttribute("success", "Selected members have been deleted successfully from the database");
    }
    else{
      session.setAttribute("danger", "You must select at least one member");
    }
    
    db.close();
  }
  catch(Exception e){
    session.setAttribute("danger", "Something wrong happened");
  }
  finally{
    response.sendRedirect("index.jsp");
  }
%>