<%-- 
    Document   : list
    Created on : Jun 7, 2024, 1:53:39 PM
    Author     : HP
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ include file="../component/header.jsp" %>
       <div class="container mt-4 ttr-wrapper">
            <h2>List Courses</h2>
             <a href="AddCourseController" class="btn btn-primary mb-4">Add Course</a>
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
            <table class="table table-striped" id="data-table">
                <thead class="thead-dark">
                    <tr>
                        <th>ID</th>
                        <th>Course Name</th>
                        <th>Course Code</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="course" items="${courses}">
                        <tr>
                            <td>${course.courseID}</td>
                            <td>${course.courseName}</td>
                            <td>${course.courseCode}</td>
                            <td>${course.status == 1 ? 'Active' : 'Inactive'}</td>
                            <td>
                                <a href="EditCourseController?courseID=${course.courseID}" class="btn btn-primary">Edit</a>
                                <a onClick="return confirm('Are your sure to delete it?')" href="DeleteCourseController?courseID=${course.courseID}" class="btn btn-danger">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

    </body>
</html>
