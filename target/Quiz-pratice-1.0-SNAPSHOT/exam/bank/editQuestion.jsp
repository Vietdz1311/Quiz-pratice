<%-- 
    Document   : editQuestion
    Created on : Sep 14, 2024, 9:59:13 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="Model.ExamQuestion" %>
<jsp:include page="../../common/head.jsp"></jsp:include>
<jsp:include page="../../common/header.jsp"></jsp:include>
<jsp:include page="../../common/menuTeacher.jsp"></jsp:include>
        <div class="container mt-4 ttr-wrapper">
        <h2>Edit Question</h2>

        <form action="ExamController" method="post">
            <input type="hidden" name="action" value="updateQuestion">
            <input type="hidden" name="examQuestionID" value="${question.examQuestionID}">

            <div class="form-group">
                <label for="customQuestionText">Question Text:</label>
                <textarea name="customQuestionText" id="customQuestionText" class="form-control" rows="3" required>${question.customQuestionText}</textarea>
            </div>

            <div class="form-group">
                <label for="questionType">Question Type:</label>
                <input type="text" name="questionType" id="questionType" class="form-control" value="${question.questionType}" required>
            </div>

            <div class="form-group">
                <label for="marksAllocated">Marks Allocated:</label>
                <input type="number" name="marksAllocated" id="marksAllocated" class="form-control" value="${question.marksAllocated}" required>
            </div>

            <button type="submit" class="btn btn-primary">Update Question</button>
        </form>

        <a href="ExamController?examID=${question.examID}" class="btn btn-secondary mt-3">Back to Exam Questions</a>
    </div>
</body>
</html>
