<jsp:include page="include/header.jsp" />
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
<jsp:include page="include/footer.jsp" />