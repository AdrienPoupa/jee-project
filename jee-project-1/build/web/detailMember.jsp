<%@page import="java.sql.SQLException"%>
<%@page import="m1.jee.model.BeanMember"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Connection"%>
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
      StringBuilder inQuery = new StringBuilder();
      
      if (select != null && select.length != 0) {
        for (int i = 0; i < select.length; i++) {
          if(i < select.length -1){
            inQuery.append(select[i]).append(", ");
          }
          else{
            inQuery.append(select[i]);
          }
        }
     
        Statement statement = db.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet data = statement.executeQuery("SELECT * FROM members WHERE ID IN (" + inQuery + ")");
        
        List<BeanMember> memberList = new ArrayList<BeanMember>();
        
        while(data.next()){
          BeanMember member = new BeanMember();
          member.setName(data.getString("NAME"));
          member.setFirstName(data.getString("FIRSTNAME"));
          member.setEmail(data.getString("EMAIL"));
          member.setTelHome(data.getString("TELHOME"));
          member.setTelMob(data.getString("TELMOB"));
          member.setTelPro(data.getString("TELPRO"));
          member.setAddress(data.getString("ADRESS"));
          member.setPostalCode(data.getString("POSTALCODE"));
          member.setCity(data.getString("CITY"));
          memberList.add(member);
        }
        
        db.close();

        if(memberList.size() > 0){
          for(BeanMember mem : memberList){
  %>
            <h2>
              Member 
              <% out.println(mem.getFirstName()); %> 
              <% out.println(mem.getName()); %>
            </h2>
            <label>Name</label>
            <input type="text" name="lastName" value="<% out.println(mem.getName()); %>"/><br/>
            <label>First name</label>
            <input type="text" name="firstName" value="<% out.println(mem.getFirstName()); %>"/><br/>
            <label>Home phone</label>
            <input type="text" name="homeNumber" value="<% out.println(mem.getTelHome()); %>"/><br/>
            <label>Mobile phone</label>
            <input type="text" name="mobileNumber" value="<% out.println(mem.getTelMob()); %>"/><br/>
            <label>Office number</label>
            <input type="text" name="officeNumber" value="<% out.println(mem.getTelPro()); %>"/><br/>
            <label>Address</label>
            <input type="text" name="address" value="<% out.println(mem.getAddress()); %>"/><br/>
            <label>Postal code</label>
            <input type="text" name="postalCode" value="<% out.println(mem.getPostalCode()); %>"/><br/>
            <label>City</label>
            <input type="text" name="city" value="<% out.println(mem.getCity()); %>"/><br/>
            <label>Email</label>
            <input type="text" name="email" value="<% out.println(mem.getEmail()); %>"/><br/><br/>
  <%
          }
        }
        else{
          session.setAttribute("danger", "No members found");
          response.sendRedirect("index.jsp");
        }
      }
      else{
        session.setAttribute("danger", "You must select at least one member");
        response.sendRedirect("index.jsp");
      }
    }
    catch(SQLException e){
      session.setAttribute("danger", "Something wrong happened");
      response.sendRedirect("index.jsp");
    }
  %>
  <br />
  <a href="index.jsp">Go back</a>
</div>
<%@include file="include/footer.jsp" %>