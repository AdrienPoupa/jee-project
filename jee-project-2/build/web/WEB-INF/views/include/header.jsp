<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="style.css">
    <title>JEE project M1</title>
  </head>
  <body>
    <header>
      <a href="/">
        <h1>JEE project M1</h1>
      </a>
    </header>
    <c:if test="${info != null}">
      <div class="error info"><c:out value="${info}"/></div>
    </c:if>
    <c:if test="${danger != null}">
      <div class="error danger"><c:out value="${danger}"/></div>
    </c:if>
    <c:if test="${success != null}">
      <div class="error success"><c:out value="${success}"/></div>
    </c:if>
    <%
      session.removeAttribute("info");
      session.removeAttribute("danger");
      session.removeAttribute("success");
    %>

