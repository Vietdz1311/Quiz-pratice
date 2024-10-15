<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="Model.ExamQuestion" %>
<jsp:include page="../../common/head.jsp"></jsp:include>
<jsp:include page="../../common/header.jsp"></jsp:include>
<jsp:include page="../../common/menuTeacher.jsp"></jsp:include>
    <div class="container mt-4 ttr-wrapper">
        <h2><c:choose>
            <c:when test="${empty exam}">Add New Exam</c:when>
            <c:otherwise>Edit Exam</c:otherwise>
        </c:choose></h2>

    <form action="ExamController" method="post">
        <input type="hidden" name="action" value="${exam != null ? 'update' : 'insert'}" />
        <input type="hidden" name="examID" value="${exam.examID}">
        <input type="hidden" name="classID" value="${1}">

        <div class="form-group">
            <label for="courseID">Course ID:</label>
            <select name="courseID" id="courseID" class="form-control" required>
                <c:forEach var="course" items="${courses}">
                    <option value="${course.courseID}" <c:if test="${course.courseID == exam.courseID}">selected</c:if>>${course.courseName}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="examName">Exam Name:</label>
            <input type="text" name="examName" id="examName" class="form-control" value="${exam.examName}" required>
        </div>

        <div class="form-group">
            <label for="examType">Exam Type:</label>
            <c:if test="${exam != null && exam.examQuestion.size() >0}">
                <input type="text" name="examType" id="examType" class="form-control" value="${exam.examType}" readonly>
            </c:if>
            <c:if test="${exam == null || exam.examQuestion.size() == 0}">
                <select name="examType" id="examType" class="form-control" required>
                    <option value="" disabled selected>Select exam type</option>
                    <option value="Multiple Choice" ${exam.examType == 'Multiple Choice' ? 'selected' : ''}>Multiple Choice</option>
                    <option value="Essay" ${exam.examType == 'Essay' ? 'selected' : ''}>Essay</option>
                </select>
            </c:if>
        </div>


        <div class="form-group">
            <label for="totalQuestions">Total Questions:</label>
            <input type="number" name="totalQuestions" id="totalQuestions" class="form-control" value="${exam.totalQuestions}" required>
        </div>

        <div class="form-group">
            <label for="maxMarks">Max Marks:</label>
            <input type="number" name="maxMarks" id="maxMarks" class="form-control" value="${exam.maxMarks}" required>
        </div>

        <div class="form-group">
            <label for="examDate">Exam Date:</label>
            <input type="datetime-local" name="examDate" id="examDate" class="form-control" value="${exam.examDate}" required>
        </div>

        <div class="form-group">
            <label for="duration">Duration (in minutes):</label>
            <input type="number" name="duration" id="duration" class="form-control" value="${exam.duration}" required>
        </div>

        <div class="form-check">
            <input type="checkbox" name="allowViewScore" id="allowViewScore" class="form-check-input" <c:if test="${exam.allowViewScore}">checked</c:if>>
                <label for="allowViewScore" class="form-check-label">Allow View Score</label>
            </div>

            <div class="form-check">
                <input type="checkbox" name="allowViewAnswers" id="allowViewAnswers" class="form-check-input" <c:if test="${exam.allowViewAnswers}">checked</c:if>>
                <label for="allowViewAnswers" class="form-check-label">Allow View Answers</label>
            </div>

            <button type="submit" class="btn btn-primary mt-3">Submit</button>
        </form>

        <a href="ExamController" class="btn btn-danger mt-3">Back to Exam List</a>

        <div class="mt-4">
        <c:if test="${exam != null}">
            <h3>Manage Questions</h3>
            <a href="ExamController?action=viewQuestions&examID=${exam.examID}" class="btn btn-info">View Questions</a>
        </c:if>
    </div>
</div>
</div>
</body>
</html>
