<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="m1.jee.model.BeanMember"%>
<%@include file="include/header.jsp" %>
<div id="body">
  <h2>List of members of the Java EE - M1</h2>
  <%!
    ResultSet data;
    Connection db;
  %>  
  <%
    String url = "jdbc:derby://localhost:1527/jee-project";
    String user = "login";
    String password = "password";

    try{
      db = DriverManager.getConnection(url, user, password);

      Statement statement = db.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      data = statement.executeQuery("SELECT * FROM members");
    }
    catch(Exception e){
      out.println(e.getMessage());
    }
    
    if(!data.next()){
  %>
      <div class="error info">The Club has no member!</div>
      <a href="addMember.jsp" class="button">Add new members</a>
  <%
    }
    
    data.beforeFirst();
  %>  
          
  <form method="post" action="deleteMember.jsp" name="formMember">    
    <table class="table" cellpadding="0">
      <thead>
        <tr>
          <th class="icon">Sel</th>
          <th class="">First name</th>
          <th class="">Last name</th>
          <th class="">Email</th>
        </tr>
      </thead>
      <tbody>
        <%
            if(data.next()){
              data.beforeFirst();
              
              while(data.next()){
        %>
                <tr>
                  <td class="icon">
                     <input type="checkbox" name="member" value="<% out.println(data.getString("ID")); %>"/>
                  </td>
                  <td class=""><% out.println(data.getString("FIRSTNAME")); %></td>
                  <td class=""><% out.println(data.getString("NAME")); %></td>
                  <td class=""><% out.println(data.getString("EMAIL")); %></td>
                </tr>
        <%
              }
            }
            else{
        %>
              <tr>
                <td class="empty" colspan="4">The Club has no member!</td>
              </tr> 
        <%
            }
        %>
      </tbody>
    </table>
    <%
      data.beforeFirst();
      
      if(data.next()) {
    %>    
      <input onClick="detailMember()" class="button" type="button" value="Details"/>
      <input onClick="deleteMember()" class="button" type="button" value="Delete"/>
    <%
      }

      db.close();
    %>
  </form>  
</div>
<script type="text/javascript">
  function detailMember(){
    document.formMember.action = "detailMember.jsp";
    document.formMember.submit();
  }
  
  function deleteMember(){
    document.formMember.action = "deleteMember.jsp";
    document.formMember.submit();
  }
</script>
<%@include file="include/footer.jsp" %>