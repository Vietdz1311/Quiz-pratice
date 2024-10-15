<%-- 
    Document   : list
    Created on : Jun 7, 2024, 11:54:49 AM
    Author     : HP
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../component/header.jsp" %>
<div class="container mt-4 ttr-wrapper">
    <h2>List Teachers</h2>
    <a href="AddTeacherController" class="btn btn-primary mb-4">Add new</a>
    <c:if test="${param.error != null}">
        <div class="alert alert-danger" role="alert">
            ${param.error}
        </div>
    </c:if>
    <c:if test="${param.success != null}">
        <div class="alert alert-success" role="alert">
            ${param.success}
        </div>
    </c:if>
    <table class="table" id="data-table">
        <thead class="thead-dark">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="teacher" items="${teachers}" varStatus="index">
                <tr>
                    <td>${index.index + 1}</td>
                    <td>${teacher.name}</td>
                    <td>${teacher.email}</td>
                    <td>${teacher.phone}</td>
                    <td>${teacher.status == 1 ? 'Active' : 'Inactive'}</td>
                    <td>
                        <a href="EditTeacherController?teacherID=${teacher.teacherID}" class="btn btn-info">Edit</a>
                        <a onClick="return confirm('Are your sure to delete it?')" href="DeleteTeacherController?teacherID=${teacher.teacherID}" class="btn btn-danger">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
