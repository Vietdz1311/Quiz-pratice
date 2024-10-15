<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="Model.ExamQuestion" %>
<jsp:include page="../../common/head.jsp"></jsp:include>
<jsp:include page="../../common/header.jsp"></jsp:include>
<jsp:include page="../../common/menu.jsp"></jsp:include>
        <div class="container mt-4 ttr-wrapper">
        <h2>List of Exams</h2>

        <a href="ExamController?action=new" class="btn btn-primary mb-3">Add New Exam</a>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">
                ${errorMessage}
            </div>
        </c:if>
        <c:choose>
            <c:when test="${not empty listExam}">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Exam ID</th>
                            <th>Course Name</th>
                            <th>Exam Name</th>
                            <th>Exam Type</th>
                            <th>Total Questions</th>
                            <th>Max Marks</th>
                            <th>Exam Date</th>
                            <th>Duration</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="exam" items="${listExam}">
                            <tr>
                                <td>${exam.examID}</td>
                                <td>${exam.course.courseName}</td>
                                <td>${exam.examName}</td>
                                <td>${exam.examType}</td>
                                <td>${exam.totalQuestions}</td>
                                <td>${exam.maxMarks}</td>
                                <td>${exam.examDate}</td>
                                <td>${exam.duration} minutes</td>
                                <td>
                                    <a href="ExamController?action=view&examID=${exam.examID}" class="btn btn-info btn-sm">View</a>
                                    <a href="ExamController?action=edit&examID=${exam.examID}" class="btn btn-warning btn-sm">Edit</a>
                                    <a href="ExamController?action=viewQuestions&examID=${exam.examID}" class="btn btn-primary btn-sm">Manage Questions</a>
                                    <form action="ExamController" method="post" style="display:inline;">
                                        <input type="hidden" name="action" value="delete">
                                        <input type="hidden" name="examID" value="${exam.examID}">
                                        <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <p>No exams available.</p>
            </c:otherwise>
        </c:choose>

        <a href="index.jsp" class="btn btn-secondary mt-3">Back to Home</a>
    </div>
</body>
</html>
