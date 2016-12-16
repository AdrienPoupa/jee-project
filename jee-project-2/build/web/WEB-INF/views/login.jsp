<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="include/header.jsp"></c:import>
<div class="login">
  <div class="title">
    <h2>Enter your credentials</h2>
  </div>
  <div class="content">
    <form method="post" action="/login">
      <input type="text" placeholder="Username" name="username"/>
      <input type="password" placeholder="Password" name="password"/>
      <input type="submit" value="Login"/>
    </form>
  </div>  
</div>
<c:import url="include/footer.jsp"></c:import>