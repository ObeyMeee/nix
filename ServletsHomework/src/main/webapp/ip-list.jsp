<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<body>
<table border="2">
    <thead>
    <tr>
        <th>IP</th>
        <th>User-Agent</th>
        <th>Time of request</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${list}" var="userInfo">
        <tr>
            <td><strong>${userInfo.ip}</strong></td>
            <td><strong>${userInfo.userAgent}</strong></td>
            <td>${userInfo.localDateTime}</td>
        </tr>
    </c:forEach>

    </tbody>
</table>
</body>
</html>