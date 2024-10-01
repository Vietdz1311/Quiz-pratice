<%-- 
    Document   : edit
    Created on : Jun 7, 2024, 4:04:13 PM
    Author     : HP
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
         <%@ include file="../component/header.jsp" %>
        <div class="container mt-4 ttr-wrapper">
            <h2>Add Classs</h2>
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
            <form action="AddClassController" method="post" class="mb-4">
                <div class="form-group">
                    <label for="className">Class Name:</label>
                    <input type="text" class="form-control" id="className" name="className" required>
                </div>
                <div class="form-group">
                    <label for="status">Status:</label>
                    <select class="form-control" id="status" name="status" required>
                        <option value="1">Active</option>
                        <option value="0">Inactive</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="teacherID">Teacher:</label>
                    <select class="form-control" id="teacherID" name="teacherID" required>
                        <c:forEach var="teacher" items="${teachers}">
                            <option value="${teacher.teacherID}">${teacher.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="courseID">Course:</label>
                    <select class="form-control" id="courseID" name="courseID" required>
                        <c:forEach var="course" items="${courses}">
                            <option value="${course.courseID}">${course.courseName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="courseID">Semester: </label>
                    <select class="form-control" id="semesterID" name="semesterID" required>
                        <c:forEach var="semester" items="${semesters}">
                            <option value="${semester.semesterID}">${semester.semesterName}</option>
                        </c:forEach>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary">Add Class</button>
            </form>
        </div>
    </body>
</html>
