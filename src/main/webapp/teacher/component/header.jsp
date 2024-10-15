<%-- 
    Document   : header
    Created on : Jun 7, 2024, 7:43:51 PM
    Author     : HP
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="getTeacherHeader" class="DAO.TeacherDAO" />
<c:set value="${getTeacherHeader.getTeacher(sessionScope.teacherID)}" var="teacherLoginHeader"/>
<c:if test="${teacherLoginHeader == null || teacherLoginHeader.status == 0}">
    <c:redirect url="LoginController" />
</c:if>
<%@include file="../../common/head.jsp" %>
<link rel="stylesheet" href="https://cdn.datatables.net/2.0.8/css/dataTables.bootstrap4.css"/>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.datatables.net/2.0.8/js/dataTables.js"></script>
<script src="https://cdn.datatables.net/2.0.8/js/dataTables.bootstrap5.js"></script>
<script>
    $(document).ready(function () {
        $("#data-table").DataTable();
    });
</script>
<%@include file="../../common/header.jsp" %>
<%@include file="../../common/menuTeacher.jsp" %>