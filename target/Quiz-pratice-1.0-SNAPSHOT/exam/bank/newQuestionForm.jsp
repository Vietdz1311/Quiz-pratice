<%-- 
    Document   : newQuestionForm
    Created on : Sep 14, 2024, 10:29:27 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="Model.ExamQuestion" %>
<jsp:include page="../../common/head.jsp"></jsp:include>
<jsp:include page="../../common/header.jsp"></jsp:include>
<jsp:include page="../../common/menuTeacher.jsp"></jsp:include>
        <div class="container mt-4 ttr-wrapper">
        <h2>Add New Question</h2>
        <form action="ExamController?action=addQuestion" method="post">
            <input type="hidden" name="examID" value="${examID}">
            <div class="form-group">
                <label for="questionID">Question ID:</label>
                <input type="number" name="questionID" id="questionID" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="customQuestionText">Question Text:</label>
                <input type="text" name="customQuestionText" id="customQuestionText" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="questionType">Question Type:</label>
                <input type="text" name="questionType" id="questionType" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="marksAllocated">Marks Allocated:</label>
                <input type="number" name="marksAllocated" id="marksAllocated" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-primary">Add Question</button>
            <a href="ExamController?action=viewQuestions&examID=${examID}" class="btn btn-secondary">Cancel</a>
        </form>
    </div>
</body>
</html>
