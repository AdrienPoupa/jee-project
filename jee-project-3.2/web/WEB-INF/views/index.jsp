<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="include/header.jsp"></c:import>
<div id="body">
  <h2>List of members of the Java EE - M1</h2>
  <c:if test="${memberList.size() == 0}">
    <div class="error info">The Club has no member!</div>
    <a href="/add" class="button">Add new members</a>
  </c:if>
    
  <form method="post" name="formMember">    
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
        <c:choose>
          <c:when test="${memberList.size() > 0}">
            <c:forEach items="${memberList}" var="mem">
              <tr>
                <td class="icon">
                   <input type="checkbox" name="member" value="<c:out value="${mem.getId()}"/>"/>
                </td>
                <td class=""><c:out value="${mem.getFirstName()}"/></td>
                <td class=""><c:out value="${mem.getName()}"/></td>
                <td class=""><c:out value="${mem.getEmail()}"/></td>
              </tr>
            </c:forEach>
          </c:when>
          <c:otherwise>
            <tr>
              <td class="empty" colspan="4">The Club has no member!</td>
            </tr> 
          </c:otherwise>
        </c:choose>  
      </tbody>
    </table>
    <c:if test="${memberList.size() > 0}">
      <input class="button" formaction="/see" type="submit" value="Details"/>
      <input class="button" formaction="/delete" type="submit" value="Delete"/>
    </c:if>
  </form>
</div>
<c:import url="include/footer.jsp"></c:import>