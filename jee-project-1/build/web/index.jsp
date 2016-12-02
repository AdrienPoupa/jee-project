<%
  if(session.getAttribute("logged") != null){
    //session.removeAttribute("logged");
%>
    <%@include file="listMember.jsp" %>
<%
  }
  else{
%>
    <%@include file="loginForm.jsp" %>
<%
  }
%>