<%-- 
    Document   : list
    Created on : Jun 7, 2024, 4:19:32 PM
    Author     : HP
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
        <%@ include file="../component/header.jsp" %>
        <div class="container mt-4 ttr-wrapper">
            <h2>List of Semesters</h2>
            <a href="AddSemesterController" class="btn btn-primary mb-4">Add Semester</a>
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
            <table class="table table-bordered" id="data-table">
                <thead class="thead-dark">
                    <tr>
                        <th>ID</th>
                        <th>Semester Name</th>
                        <th>Year</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="semester" items="${semesters}" varStatus="index">
                        <tr>
                            <td>${index.index + 1}</td>
                            <td>${semester.semesterName}</td>
                            <td>${semester.year}</td>
                            <td>${semester.status == 1 ? "Active" : "Inactive"}</td>
                            <td>
                                <a href="EditSemesterController?semesterID=${semester.semesterID}" class="btn btn-warning">Edit</a>
                                <a onClick="return confirm('Are your sure to delete it?')" href="DeleteSemesterController?semesterID=${semester.semesterID}" class="btn btn-danger">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
