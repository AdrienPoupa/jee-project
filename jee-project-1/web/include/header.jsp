<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="style.css">
    <title>JEE project M1</title>
  </head>
  <body>
    <header>
      <a href="index.jsp">
        <h1>JEE project M1</h1>
      </a>
    </header>
    <%
      if(session.getAttribute("info") != null){
    %>
      <div class="error info"><% out.println(session.getAttribute("info")); %></div>
    <%
      }

      if(session.getAttribute("danger") != null){
    %>
      <div class="error danger"><% out.println(session.getAttribute("danger")); %></div>
    <%
      }

      if(session.getAttribute("success") != null){
    %>
      <div class="error  success"><% out.println(session.getAttribute("success")); %></div>
    <%
      }
    %>

