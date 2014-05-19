<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 
    Document   : header
    Created on : 6-mag-2014, 13.18.37
    Author     : confalonieri
--%>

<c:choose>
    <c:when test="${usuario == null}">
        <jsp:forward page="login.jsp"/>
    </c:when>
</c:choose>

<br><div class="container-fluid text-right"><a class="btn btn-danger" href="logout">Logout</a></div>



