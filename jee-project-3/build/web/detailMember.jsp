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
        
        List<BeanMember> memberList = new ArrayList<BeanMember>();
        
        while(data.next()){
          BeanMember member = new BeanMember();
          member.setName(data.getString("NAME"));
          member.setFirstName(data.getString("FIRSTNAME"));
          member.setFirstName(data.getString("EMAIL"));
          member.setTelHome(data.getString("TELHOME"));
          member.setTelMob(data.getString("TELMOB"));
          member.setTelPro(data.getString("TELPRO"));
          member.setAddress(data.getString("ADRESS"));
          member.setPostalCode(data.getString("POSTALCODE"));
          member.setPostalCode(data.getString("EMAIL"));
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
            <input type="text" name="city" value="<% out.println(mem.getCity()); %>"/><br/><br/>
            <label>Email</label>
            <input type="text" name="email" value="<% out.println(mem.getEmail()); %>"/><br/><br/>
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
    }
    catch(Exception e){
      out.println(e.getMessage());
      session.setAttribute("danger", "Something wrong happened");
    }
  %>
  <br />
  <a href="index.jsp">Go back</a>
</div>
<%@include file="include/footer.jsp" %>