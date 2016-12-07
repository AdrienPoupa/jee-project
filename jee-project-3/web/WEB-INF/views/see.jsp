<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="include/header.jsp"></c:import>
<div id="body">
  <c:choose>
    <c:when test="${memberList.size() > 0}">
      <c:forEach items="${memberList}" var="mem">
        <h2>
          Member 
          <c:out value="${mem.getFirstName()}"/>
          <c:out value="${mem.getName()}"/>
        </h2>
        <label>Name</label>
        <input type="text" name="lastName" value="<c:out value="${mem.getName()}"/>"/><br/>
        <label>First name</label>
        <input type="text" name="firstName" value="<c:out value="${mem.getFirstName()}"/>"/><br/>
        <label>Home phone</label>
        <input type="text" name="homeNumber" value="<c:out value="${mem.getTelHome()}"/>"/><br/>
        <label>Mobile phone</label>
        <input type="text" name="mobileNumber" value="<c:out value="${mem.getTelMob()}"/>"/><br/>
        <label>Office number</label>
        <input type="text" name="officeNumber" value="<c:out value="${mem.getTelPro()}"/>"/><br/>
        <label>Address</label>
        <input type="text" name="address" value="<c:out value="${mem.getAddress()}"/>"/><br/>
        <label>Postal code</label>
        <input type="text" name="postalCode" value="<c:out value="${mem.getPostalCode()}"/>"/><br/>
        <label>City</label>
        <input type="text" name="city" value="<c:out value="${mem.getCity()}"/>"/><br/>
        <label>Email</label>
        <input type="text" name="email" value="<c:out value="${mem.getEmail()}"/>"/><br/><br/>
      </c:forEach>
    </c:when>
  </c:choose>
  <br />      
  <a href="/">Go back</a>
</div>
<c:import url="include/footer.jsp"></c:import>