<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Array"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%
  if(session.getAttribute("logged") == null){
    response.sendRedirect("index.jsp");
  }
%>
<%@include file="include/header.jsp" %>
<div id="body">
  <%
    Connection db;
 
    String url = "jdbc:derby://localhost:1527/jee-project";
    String user = "login";
    String password = "password";

    try{
      db = DriverManager.getConnection(url, user, password);
      String select[] = request.getParameterValues("member");
      String inQuery = "";
      
      for (int i = 0; i < select.length; i++) {
        if(i < select.length -1){
          inQuery += select[i] + ", ";
        }
        else{
          inQuery += select[i];
        }
      }
      
      if(select.length > 0){
        Statement statement = db.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet data = statement.executeQuery("SELECT * FROM members WHERE ID IN (" + inQuery + ")");

        if(data.next()){
          data.beforeFirst();
          
          while(data.next()){
  %>
            <h2>
              Member 
              <% out.println(data.getString("FIRSTNAME")); %> 
              <% out.println(data.getString("NAME")); %>
            </h2>
            <label>Name</label>
            <input type="text" name="lastName" value="<% out.println(data.getString("NAME")); %>"/><br/>
            <label>First name</label>
            <input type="text" name="firstName" value="<% out.println(data.getString("FIRSTNAME")); %>"/><br/>
            <label>Home phone</label>
            <input type="text" name="homeNumber" value="<% out.println(data.getString("TELHOME")); %>"/><br/>
            <label>Mobile phone</label>
            <input type="text" name="mobileNumber" value="<% out.println(data.getString("TELMOB")); %>"/><br/>
            <label>Office number</label>
            <input type="text" name="officeNumber" value="<% out.println(data.getString("TELPRO")); %>"/><br/>
            <label>Address</label>
            <input type="text" name="address" value="<% out.println(data.getString("ADRESS")); %>"/><br/>
            <label>Postal code</label>
            <input type="text" name="postalCode" value="<% out.println(data.getString("POSTALCODE")); %>"/><br/>
            <label>Email</label>
            <input type="text" name="city" value="<% out.println(data.getString("EMAIL")); %>"/><br/><br/>
  <%
          }
        }
        else{
          session.setAttribute("info", "No members found");
        }
      }
      else{
        session.setAttribute("info", "You must select at least one member");
      }
      
      db.close();
    }
    catch(Exception e){
      out.println(e.getMessage());
      session.setAttribute("danger", "Something wrong happened");
    }
    finally{
      //response.sendRedirect("index.jsp");
    }
  %>
</div>
<%@include file="include/footer.jsp" %>