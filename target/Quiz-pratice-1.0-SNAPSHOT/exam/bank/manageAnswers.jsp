<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="Model.ExamQuestion" %>
<jsp:include page="../../common/head.jsp"></jsp:include>
<jsp:include page="../../common/header.jsp"></jsp:include>
<jsp:include page="../../common/menu.jsp"></jsp:include>
    <div class="container mt-4 ttr-wrapper">
        <h2>Manage Question and Answers for Question ID: ${examQuestionID}</h2>
    <h3>${examQuestion.questionID == 0 ? "Edit Question" :  "Question of bank"}</h3>
    <form id="questionForm" action="ExamController" method="post">
        <input type="hidden" name="action" value="updateQuestion">
        <input type="hidden" name="examQuestionID" value="${examQuestionID}">

        <div class="form-group">
            <label for="questionText">Question Text:</label>
            <textarea ${examQuestion.questionID != 0 ? "readonly" : ""} name="questionText" id="questionText" rows="3" class="form-control">${examQuestion.customQuestionText}</textarea>
        </div>
        <c:if test="${examQuestion.questionID == 0}">
            <button type="submit" class="btn btn-primary">Update Question</button>
        </c:if>
    </form>
    <hr>
    <c:if test="${examQuestion.questionType == 'Multiple Choice'}">
        <h3>Manage Answers</h3>
        <form id="answersForm" action="ExamController" method="post" onsubmit="return validateAnswers()">
            <input type="hidden" name="action" value="updateAnswers">
            <input type="hidden" name="examQuestionID" value="${examQuestionID}">
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Answer Text</th>
                        <th>Is Correct</th>
                            <c:if test="${examQuestion.questionID == 0}">
                            <th>Actions</th>
                            </c:if>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="answer" items="${answers}" varStatus="index">
                        <tr>
                            <td>
                                <input type="text" name="answerTexts" value="${answer.answerText}" class="form-control">
                            </td>
                            <td>
                                <input type="checkbox" name="isCorrects[${index.index}]" ${answer.isCorrect ? "checked" : ""} ${examQuestion.questionID == 0 ? "" : "disabled"}>
                            </td>
                            <c:if test="${examQuestion.questionID == 0}">
                                <td>
                                    <a class="btn btn-danger" href="ExamController?action=deleteAnswer&deleteAnswer=${answer.customAnswerID}&examQuestionID=${examQuestionID}">Delete</a>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                    <c:if test="${examQuestion.questionID != 0 && examQuestion.questionType == 'Multiple Choice'}">
                        <c:forEach var="answer" items="${examQuestion.questionBank.anwsers}">
                            <tr>
                                <td>
                                    <input type="text" name="answerTexts" value="${answer.answerText}" class="form-control" readonly/>
                                </td>
                                <td>
                                    <input type="checkbox" name="isCorrects" ${answer.isCorrect ? "checked" : ""} disabled>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                </tbody>
            </table>
            <c:if test="${examQuestion.questionID == 0}">
                <button type="submit" class="btn btn-success">Save Changes</button>
            </c:if>
        </form>
    </c:if>
    <c:if test="${examQuestion.questionID == 0 && examQuestion.questionType == 'Multiple Choice'}">
        <h3>Add New Answer</h3>
        <form action="ExamController" method="post">
            <input type="hidden" name="action" value="addAnswer">
            <input type="hidden" name="examQuestionID" value="${examQuestionID}">

            <div class="form-group">
                <label for="newAnswerText">New Answer Text:</label>
                <input type="text" name="newAnswerText" id="newAnswerText" class="form-control">
            </div>
            <div class="form-group">
                <label for="isCorrect">Is Correct:</label>
                <input type="checkbox" name="isCorrect" id="isCorrect">
            </div>

            <button type="submit" class="btn btn-primary">Add Answer</button>
        </form>
    </c:if>
</div>
<script>
    function validateAnswers() {
        var isCorrectCheckboxes = document.querySelectorAll('input[name^="isCorrects"]:checked');
        if (isCorrectCheckboxes.length === 0) {
            alert('Please select at least one correct answer.');
            return false;
        }
        return true;
    }
</script>
</body>
</html>
