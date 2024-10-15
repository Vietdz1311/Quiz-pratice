<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="Model.ExamQuestion" %>
<jsp:include page="../../common/head.jsp"></jsp:include>
<jsp:include page="../../common/header.jsp"></jsp:include>
<jsp:include page="../../common/menu.jsp"></jsp:include>

    <div class="container mt-4 ttr-wrapper">
        <style>
        <c:if test="${exam.examType != 'Multiple Choice'}">
            .answers-container {
                visibility: hidden;
                height: 0;
                overflow: hidden;
            }
        </c:if>

        .answer-row {
            margin-bottom: 10px;
        }
        #questionIDs {
            height: 100%;
        }
    </style>
    <h2>Manage Questions for Exam ID ${examID}</h2>
    <h4>Add Multiple Questions from Bank</h4>
    <form action="ExamController" method="post">
        <input type="hidden" name="action" value="addMultipleQuestions">
        <input type="hidden" name="examID" value="${examID}">
        <div class="form-group">
            <label for="questionIDs">Select Questions</label>
            <select name="questionIDs" id="questionIDs" class="form-control" multiple required>
                <c:forEach var="question" items="${allQuestions}">
                    <option value="${question.questionID}" 
                            ${fn:contains(selectedQuestionIDs, question.questionID) ? 'selected' : ''}>
                        ${question.questionText}
                    </option>
                </c:forEach>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Add Selected Questions</button>
    </form>

    <h4>Add Custom Questions</h4>
    <form id="customQuestionsForm" action="ExamController" method="post">
        <input type="hidden" name="action" value="addCustomQuestions">
        <input type="hidden" name="examID" value="${examID}">
        <div id="customQuestionsContainer">
            <c:forEach var="exam" items="${exams}">
                <c:choose>
                    <c:when test="${exam.examID == examID}">
                        <c:set var="examType" value="${exam.examType}" />
                    </c:when>
                </c:choose>
            </c:forEach>
            <div class="card custom-question-card mb-3">
                <div class="card-body">
                    <div class="d-flex justify-content-between">
                        <h5 class="card-title">Custom Question</h5>
                        <button type="button" class="btn btn-danger btn-sm" onclick="removeCustomQuestion(this)">Remove</button>
                    </div>
                    <div class="form-group">
                        <label for="customQuestionText">Custom Question Text</label>
                        <textarea name="customQuestionTexts" class="form-control" rows="3" required></textarea>
                    </div>
                    <div class="form-group">
                        <label for="questionType">Question Type</label>
                        <input type="text" name="questionTypes" class="form-control" required value="${exam.examType}" readonly>
                    </div>
                    <c:if test="${exam.examType ==  'Multiple Choice'}">
                        <div class="form-group answers-container">
                            <div class="answer-row">
                                <label for="answerText">Answer Text</label>
                                <input type="text" name="answerTexts[0]" class="form-control" required>
                                <div class="form-group">
                                    <label for="isCorrect">Is Correct?</label>
                                    <input type="checkbox" name="isCorrects[0][0]" value="true">
                                </div>
                            </div>
                            <button type="button" class="btn btn-secondary mb-3" onclick="addMoreAnswers(this, 0)">Add More Answers</button>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
        <button type="button" id="addMoreCustomQuestions" class="btn btn-secondary">Add More Custom Questions</button>
        <button type="submit" class="btn btn-primary">Add Custom Questions</button>
    </form>

    <h4>Existing Questions</h4>
    <table class="table table-striped">
        <thead>
            <tr>
                <th>Question ID</th>
                <th>Question Text</th>
                <th>Question Type</th>
                <th>Marks Allocated</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="question" items="${questions}" varStatus="index">
                <tr>
                    <td>${index.index + 1}</td>
                    <td>${question.customQuestionText}</td>
                    <td>${question.questionType}</td>
                    <td>${question.marksAllocated}</td>
                    <td>
                        <form action="ExamController" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="deleteQuestion">
                            <input type="hidden" name="examQuestionID" value="${question.examQuestionID}">
                            <input type="hidden" name="examID" value="${examID}">
                            <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                        </form>
                        <form action="ExamController" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="manageAnswers">
                            <input type="hidden" name="examQuestionID" value="${question.examQuestionID}">
                            <button type="submit" class="btn btn-info btn-sm">Manage Answers</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <a href="ExamController" class="btn btn-secondary mt-3">Back to Exam List</a>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
    <c:if test="${exam.examType == 'Multiple Choice'}">
                                var container = document.querySelector('.answers-container');
                                var answerInputs = container.querySelectorAll('input');
                                container.style.visibility = 'visible';
                                container.style.height = 'auto';
                                answerInputs.forEach(function (input) {
                                    input.removeAttribute('disabled');
                                    input.required = true;
                                });
    </c:if>

                                let questionCount = 1;
                                let anwCount = 1;

                                document.getElementById('addMoreCustomQuestions').addEventListener('click', function () {
                                    anwCount = 1;
                                    let container = document.getElementById('customQuestionsContainer');
                                    let newRow = document.createElement('div');
                                    newRow.className = 'card custom-question-card mb-3';
                                    newRow.innerHTML =
                                            '<div class="card-body">' +
                                            '<div class="d-flex justify-content-between">' +
                                            '<h5 class="card-title">Custom Question</h5>' +
                                            '<button type="button" class="btn btn-danger btn-sm" onclick="removeCustomQuestion(this)">Remove</button>' +
                                            '</div>' +
                                            '<div class="form-group">' +
                                            '<label for="customQuestionText">Custom Question Text</label>' +
                                            '<textarea name="customQuestionTexts" class="form-control" rows="3" required></textarea>' +
                                            '</div>' +
                                            '<div class="form-group">' +
                                            '<label for="questionType">Question Type</label>' +
                                            '<input type="text" name="questionTypes" class="form-control" readonly value="${exam.examType}">' +
                                            '</div>' +
    <c:if test="${exam.examType ==  'Multiple Choice'}">
                                    '<div class="form-group answers-container">' +
                                            '<div class="answer-row">' +
                                            '<label for="answerText">Answer Text</label>' +
                                            '<input type="text" name="answerTexts[' + questionCount + ']" class="form-control" required>' +
                                            '<div class="form-group">' +
                                            '<label for="isCorrect">Is Correct?</label>' +
                                            '<input type="checkbox" name="isCorrects[' + questionCount + '][0]" value="true">' +
                                            '</div>' +
                                            '</div>' +
                                            '<button type="button" class="btn btn-secondary mb-3" onclick="addMoreAnswers(this, ' + questionCount + ')">Add More Answers</button>' +
                                            '</div>' +
    </c:if>
                                    '</div>';
                                    container.appendChild(newRow);
                                    questionCount++;
                                });

                                function removeCustomQuestion(button) {
                                    button.closest('.card').remove();
                                }

                                function addMoreAnswers(button, questionIndex) {
                                    let container = button.closest('.answers-container');
                                    let newAnswerRow = document.createElement('div');
                                    newAnswerRow.className = 'answer-row';
                                    newAnswerRow.innerHTML =
                                            '<label for="answerText">Answer Text</label>' +
                                            '<input type="text" name="answerTexts[' + questionIndex + ']" class="form-control" required>' +
                                            '<div class="form-group">' +
                                            '<label for="isCorrect">Is Correct?</label>' +
                                            '<input type="checkbox" name="isCorrects[' + questionIndex + '][anwCount]" value="true">' +
                                            '</div>';

                                    container.appendChild(newAnswerRow);
                                    anwCount++;
                                }
</script>

<jsp:include page="../../common/footer.jsp"></jsp:include>
</body>
</html>
