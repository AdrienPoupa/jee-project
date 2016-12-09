<%
  if(session.getAttribute("logged") != null){
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