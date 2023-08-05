<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Customer Sign Up</title>
</head>
<body>
    <h1>Customer Sign Up</h1>

    <c:if test="${violations != null}">
        <c:forEach items="${violations}" var="violation">
            <p>${violation}.</p>
        </c:forEach>
    </c:if>

    <form action="${pageContext.request.contextPath}/processcustomer" method="post">
        <label for="firstname">First Name : </label>
        <input type="text" name="firstname" id="firstname" value="${firstname}">
        <label for="lastname">Last Name:</label>
        <input type="text" name="lastname" id="lastname" value="${lastname}">
        <label for="email">Email: </label>
        <input type="text" name="email" id="email" value="${email}">
        <input type="submit" name="signup" value="Sign Up">
    </form>
</body>
</html>