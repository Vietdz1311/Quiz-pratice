<%-- 
    Document   : list
    Created on : Jun 7, 2024, 7:43:26 PM
    Author     : HP
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="getTeacher" class="DAO.TeacherDAO" />
<jsp:useBean id="getCourse" class="DAO.CourseDAO" />
        <%@ include file="../component/header.jsp" %>
        <div class="container mt-4 ttr-wrapper">
            <h2>List of Classes</h2>
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
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Class Name</th>
                        <th>Status class</th>
                        <th>Course </th>
                        <th>Semester Name</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="cla" items="${classes}" varStatus="index">
                        <c:set value="${getCourse.getCourse(cla.courseID)}" var="course" />
                        <c:set value="${getTeacher.getTeacher(cla.teacherID)}" var="teacher" />
                        <tr>
                            <td>${index.index+1}</td>
                            <td>${cla.className}</td>
                            <td>${cla.status == 1 ? 'Active' : 'Inactive'}</td>
                            <td>${course.courseCode}</td>
                            <td>${cla.semesterName}</td>
                            <td>
                                <a href="TeacherClassController?action=view&classID=${cla.classID}" class="btn btn-info">View</a>
                                <a href="TeacherClassController?action=add-student&classID=${cla.classID}" class="btn btn-danger">Add student</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
