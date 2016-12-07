<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="include/header.jsp"></c:import>
<div id="body">
  <h2>List of members of the Java EE - M1</h2>
  <c:if test="${memberList.size() == 0}">
    <div class="error info">The Club has no member!</div>
    <a href="addMember.jsp" class="button">Add new members</a>
  </c:if>
    
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
        <c:choose>
          <c:when test="${memberList.size() > 0}">
            <c:forEach items="${listMember}" var="mem">
              <tr>
                <td class="icon">
                   <input type="checkbox" name="member" value="<c:out value="${mem.getId()}"/>"/>
                </td>
                <td class=""><c:out value="${mem.getFirstName()}"/></td>
                <td class=""<c:out value="${mem.getName()}"/></td>
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
      <input onClick="detailMember()" class="button" type="button" value="Details"/>
      <input onClick="deleteMember()" class="button" type="button" value="Delete"/>
    </c:if>
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
<c:import url="include/footer.jsp"></c:import>