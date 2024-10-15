<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../common/head.jsp"></jsp:include>
<jsp:include page="../../common/header.jsp"></jsp:include>
<jsp:include page="../../common/menuTeacher.jsp"></jsp:include>
    <div class="container mt-4 ttr-wrapper">
        <h1>${question != null ? "Update" : "Add"} Question</h1>
        <form action="QuestionBankController" method="post">
            <input type="hidden" name="questionId" value="${question != null ? question.questionID : ''}" />
            <input type="hidden" name="action" value="${question != null ? 'update' : 'insert'}" />
            <div class="form-group">
                <label for="courseId">Course:</label>
                <select id="courseId" name="courseId" class="form-control" required>
                    <c:forEach var="course" items="${courses}">
                        <option value="${course.courseID}" ${question != null && course.courseID == question.courseID ? 'selected' : ''}>
                            ${course.courseName}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="questionText">Question:</label>
                <textarea id="questionText" name="questionText" class="form-control" required>${question != null ? question.questionText : ''}</textarea>
            </div>

            <div class="form-group">
                <label for="questionType">Question Type:</label>
                <c:choose>
                    <c:when test="${question != null}">
                        <input type="hidden" name="questionType" value="${question.questionType}" />
                        <input type="text" id="questionType" class="form-control" value="${question.questionType}" readonly />
                    </c:when>
                    <c:otherwise>
                        <select id="questionType" name="questionType" class="form-control" required onchange="toggleAnswerFields()">
                            <option value="Multiple Choice">Multiple Choice</option>
                            <option value="Essay">Essay</option>
                        </select>
                    </c:otherwise>
                </c:choose>
            </div>

            <div id="answerFields" class="form-group">
                <label for="answers">Answers:</label>
                <div id="answersContainer">
                    <c:forEach var="answer" items="${answers}" varStatus="index">
                        <div class="form-group answer-field">
                            <input type="text" name="answers" class="form-control" value="${answer.answerText}" required />
                            <input type="checkbox" name="correctAnswers" value="${index.index}" ${answer.isCorrect ? 'checked' : ''} />
                            <button type="button" class="btn btn-danger btn-sm remove-answer-button">Remove</button>
                        </div>
                    </c:forEach>
                    <c:if test="${empty answers}">
                        <div class="form-group answer-field">
                            <input type="text" name="answers" class="form-control" placeholder="Answer 1" required />
                            <input type="checkbox" name="correctAnswers" value="0" />
                            <button type="button" class="btn btn-danger btn-sm remove-answer-button">Remove</button>
                        </div>
                        <div class="form-group answer-field">
                            <input type="text" name="answers" class="form-control" placeholder="Answer 2" required />
                            <input type="checkbox" name="correctAnswers" value="1" />
                            <button type="button" class="btn btn-danger btn-sm remove-answer-button">Remove</button>
                        </div>
                    </c:if>
                </div>
                <button type="button" class="btn btn-secondary" id="addAnswerButton">Add more answers</button>
            </div>

            <div class="form-group">
                <label for="difficulty">Difficulty:</label>
                <input id="difficulty" type="number" name="difficulty" class="form-control" value="${question != null ? question.difficulty : ''}" required />
            </div>

            <div class="form-group">
                <label for="marks">Marks:</label>
                <input id="marks" type="number" name="marks" class="form-control" value="1" readonly />
            </div>
            <button type="submit" class="btn btn-primary">${question != null ? 'Update' : 'Add'}</button>
        </form>
    </div>

    <script>
        function toggleAnswerFields() {
            var questionType = document.getElementById("questionType").value;
            var answerFields = document.getElementById("answerFields");
            var answers = document.querySelectorAll('input[name="answers"]');

            if (questionType === "Multiple Choice") {
                answerFields.style.display = "block";
                answers.forEach(function (answer) {
                    answer.setAttribute('required', true);  // Đặt lại thuộc tính required cho các đáp án
                });
            } else {
                answerFields.style.display = "none";
                answers.forEach(function (answer) {
                    answer.removeAttribute('required');  // Loại bỏ thuộc tính required khi chọn Essay
                });
            }
        }

        window.onload = function () {
            toggleAnswerFields();  // Gọi hàm để kiểm tra trạng thái ban đầu khi tải trang
        };

        // Thêm các trường đáp án động
        document.getElementById("addAnswerButton").addEventListener("click", function () {
            var answersContainer = document.getElementById("answersContainer");
            var newAnswerCount = answersContainer.children.length + 1; // Đếm số trường hiện có
            var newAnswerDiv = document.createElement("div");
            newAnswerDiv.classList.add("form-group", "answer-field");
            newAnswerDiv.innerHTML = '<input type="text" name="answers" class="form-control" placeholder="Answer ' + newAnswerCount + '" required />' +
                    '<input type="checkbox" name="correctAnswers" value="' + (newAnswerCount - 1) + '" />' +
                    '<button type="button" class="btn btn-danger btn-sm remove-answer-button">Remove</button>';
            answersContainer.appendChild(newAnswerDiv);

            addRemoveAnswerButtonListener(newAnswerDiv.querySelector('.remove-answer-button'));
        });

        // Hàm xóa các trường đáp án
        function addRemoveAnswerButtonListener(button) {
            button.addEventListener("click", function () {
                var answerFields = document.querySelectorAll(".answer-field");
                if (answerFields.length > 2) {
                    this.parentElement.remove();
                } else {
                    alert("At least two answers are required.");
                }
            });
        }

        // Gán sự kiện xóa cho các nút Remove hiện có
        document.querySelectorAll(".remove-answer-button").forEach(function (button) {
            addRemoveAnswerButtonListener(button);
        });

        // Validate form submission
        function validateForm() {
            var questionType = document.getElementById("questionType").value;
            var answers = document.querySelectorAll('input[name="answers"]');
            var correctAnswers = document.querySelectorAll('input[name="correctAnswers"]:checked');

            if (questionType === "Multiple Choice") {
                if (answers.length < 2) {
                    alert("You must provide at least two answers.");
                    return false;
                }
                if (correctAnswers.length === 0) {
                    alert("You must select at least one correct answer.");
                    return false;
                }
            }
            return true;
        }

        document.querySelector("form").addEventListener("submit", function (event) {
            if (!validateForm()) {
                event.preventDefault(); 
            }
        });

    </script>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<jsp:include page="../../common/footer.jsp"></jsp:include>
</body>
</html>
