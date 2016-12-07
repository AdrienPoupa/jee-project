<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="m1.jee.model.BeanMember"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@include file="include/header.jsp" %>

<div id="body">
  <h2>List of members of the Java EE - M1</h2>
  <%
    ResultSet data;
    Connection db;
    
    String url = "jdbc:derby://localhost:1527/jee-project";
    String user = "login";
    String password = "password";
    List<BeanMember> memberList = new ArrayList<BeanMember>();

    try{
      db = DriverManager.getConnection(url, user, password);

      Statement statement = db.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      data = statement.executeQuery("SELECT * FROM members");

      while(data.next()){
        BeanMember member = new BeanMember();
        member.setId(data.getString("ID"));
        member.setName(data.getString("NAME"));
        member.setEmail(data.getString("EMAIL"));
        member.setFirstName(data.getString("FIRSTNAME"));
        member.setTelHome(data.getString("TELHOME"));
        member.setTelMob(data.getString("TELMOB"));
        member.setTelPro(data.getString("TELPRO"));
        member.setAddress(data.getString("ADRESS"));
        member.setPostalCode(data.getString("POSTALCODE"));
        member.setPostalCode(data.getString("EMAIL"));
        memberList.add(member);
      }
      
      db.close();
    }
    catch(Exception e){
      out.println(e.getMessage());
    }
    
    if(memberList.size() == 0){
  %>
      <div class="error info">The Club has no member!</div>
      <a href="addMember.jsp" class="button">Add new members</a>
  <%
    }
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
            if(memberList.size() > 0){
              for(BeanMember mem : memberList){
        %>
                <tr>
                  <td class="icon">
                     <input type="checkbox" name="member" value="<% out.println(mem.getId()); %>"/>
                  </td>
                  <td class=""><% out.println(mem.getFirstName()); %></td>
                  <td class=""><% out.println(mem.getName()); %></td>
                  <td class=""><% out.println(mem.getEmail()); %></td>
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
      if(memberList.size() > 0) {
    %>    
      <input onClick="detailMember()" class="button" type="button" value="Details"/>
      <input onClick="deleteMember()" class="button" type="button" value="Delete"/>
    <%
      }
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